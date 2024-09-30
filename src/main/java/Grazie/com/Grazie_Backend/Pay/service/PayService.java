package Grazie.com.Grazie_Backend.Pay.service;

import Grazie.com.Grazie_Backend.Pay.dto.MessageDTO;
import Grazie.com.Grazie_Backend.Pay.dto.PayResponseDTO;
import Grazie.com.Grazie_Backend.Pay.entity.Pay;
import Grazie.com.Grazie_Backend.Pay.repository.PayRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@Slf4j
public class PayService {

    @Value("${imp.code}")
    private String impCode;
    @Value("${imp.api.key}")
    private String impKey;
    @Value("${imp.api.secretkey}")
    private String impSecretKey;
    @Value("${imp.api.baseUrl}")
    private String impBaseUrl;
    private final PayRepository payRepository;

    @Autowired
    public PayService(PayRepository payRepository) {
        this.payRepository = payRepository;
    }

    // 아임포트 엑세스토큰 받아오기
    public String getAccessToken() {
        String impUrl = impBaseUrl + "/users/getToken";
        RestTemplate restTemplate = new RestTemplate();
        // Request Body 설정
        String requestBody = String.format("{\"imp_key\": \"%s\", \"imp_secret\": \"%s\"}", impKey, impSecretKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(impUrl, HttpMethod.POST, entity, String.class);

        // 응답에서 엑세스 토큰 추출
        if (response.getStatusCode().is2xxSuccessful()) {
            // JSON 파싱을 통해 토큰을 얻는 방법
            // 예를 들어 Gson 라이브러리를 사용하여 처리할 수 있습니다.
            return parseAccessToken(response.getBody());
        } else {
            // 에러 처리
            throw new RuntimeException("Failed to get access token: " + response.getStatusCode());
        }
    }

    // 결제 진행
    @Transactional
    public PayResponseDTO processPay(String imp) {
        PayResponseDTO payResponseDTO = getPayDetails(imp);

        if (payResponseDTO.getResponseDetails().getStatus().equals("paid")) {
            savePay(payResponseDTO);
            return payResponseDTO;
        } else {
            throw new RuntimeException("응답상태 : " + payResponseDTO.getResponseDetails().getStatus());
        }

    }

    // 결제 취소 진행
    @Transactional
    public MessageDTO processCancelPay(String imp) {
        PayResponseDTO payResponseDTO = cancelPay(imp);

        if (payResponseDTO.getResponseDetails().getStatus().equals("cancelled")) {
            cancelUpdatePay(payResponseDTO);
            return MessageDTO.builder()
                    .code(200)
                    .message("결제취소가 성공적으로 되었습니다.")
                    .build();
        } else {
            throw new RuntimeException("응답상태 : " + payResponseDTO.getResponseDetails().getStatus());
        }
    }

    // imp를 이용한 결제 단건 조회
    public PayResponseDTO getPayDetails(String imp) {
        String url = impBaseUrl + "/payments/" + imp;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String accessToken = getAccessToken();

        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
//            log.info("결제 정보 조회 성공: {}", response.getBody());
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.getBody(), PayResponseDTO.class);
            } catch (Exception e) {
                throw new RuntimeException("JSON 변환 실패: " + e.getMessage());
            }
        } else {
            log.error("결제 정보 조회 실패: {}", response.getStatusCode());
            throw new RuntimeException("Failed to get payment details: " + response.getStatusCode());
        }
    }

    // 유저의 모든 결제내역 조회
    public List<Pay> getAllPayByBuyerName(String buyerName) {
        return payRepository.findAllByBuyerName(buyerName);
    }

    // imp를 이용한 결제 취소
    public PayResponseDTO cancelPay(String imp) {
        String url = impBaseUrl + "/payments/cancel";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String accessToken = getAccessToken();

        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 본문에 imp_uid 추가
        String requestBody = "{\"imp_uid\":\"" + imp + "\"}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info(response.getBody());
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.getBody(), PayResponseDTO.class);
            } catch (Exception e) {
                throw new RuntimeException("JSON 변환 실패: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("Failed to get payment details: " + response.getStatusCode());
        }
    }

    // 결제정보 DB 저장
    private void savePay(PayResponseDTO dto) {
        Pay pay = new Pay();
        pay.setImpUid(dto.getResponseDetails().getImpUid());
        pay.setMerchantUid(dto.getResponseDetails().getMerchantUid());
        pay.setAmount(dto.getResponseDetails().getAmount());
        pay.setCancelAmount(dto.getResponseDetails().getCancelAmount());
        pay.setCurrency(dto.getResponseDetails().getCurrency());
        pay.setStatus(dto.getResponseDetails().getStatus());
        pay.setPaidAt(dto.getResponseDetails().getPaidAt());
        pay.setBuyerName(dto.getResponseDetails().getBuyerName());
        pay.setBuyerEmail(dto.getResponseDetails().getBuyerEmail());
        pay.setPayMethod(dto.getResponseDetails().getPayMethod());
        pay.setPgProvider(dto.getResponseDetails().getPgProvider());
        pay.setCardName(dto.getResponseDetails().getCardName());
        pay.setCancelledAt(dto.getResponseDetails().getCancelledAt());
        pay.setFailedAt(dto.getResponseDetails().getFailedAt());

        payRepository.save(pay);
    }

    // 결제 취소 후 DB 업데이트
    private void cancelUpdatePay(PayResponseDTO dto) {
        Pay pay = payRepository.findByMerchantUid(dto.getResponseDetails().getMerchantUid())
                .orElseThrow(() -> new RuntimeException("결제내역을 찾을 수 없습니다"));

        pay.setCancelAmount(dto.getResponseDetails().getCancelAmount());
        pay.setCancelledAt(dto.getResponseDetails().getCancelledAt());
        pay.setStatus(dto.getResponseDetails().getStatus());

        payRepository.save(pay);
    }

    // response에서 accessToken 가져오기
    private String parseAccessToken(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            return jsonNode.path("response").path("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse access token", e);
        }
    }
}
