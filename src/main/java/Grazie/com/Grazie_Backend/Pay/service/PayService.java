package Grazie.com.Grazie_Backend.Pay.service;

import Grazie.com.Grazie_Backend.Pay.entity.Pay;
import Grazie.com.Grazie_Backend.Pay.repository.PayRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    // imp를 이용한 결제 단건 조회
    public String getPayDetails(String imp) {
        String url = impBaseUrl + "/payments/" + imp;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String accessToken = getAccessToken();

        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info(String.valueOf(response));
            return response.getBody(); // 응답 본문 반환
        } else {
            throw new RuntimeException("Failed to get payment details: " + response.getStatusCode());
        }
    }

    // 유저의 모든 결제내역 조회
    public List<Pay> getAllPayByBuyerName(String buyerName) {
        String url = impBaseUrl + "/payments";
        List<Pay> pays = payRepository.findAllByBuyerName(buyerName);

        return pays;
    }

    // imp를 이용한 결제 취소
    public String cancelPay(String imp) {
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
            log.info(String.valueOf(response));

            return response.getBody(); // 응답 본문 반환
        } else {
            throw new RuntimeException("Failed to get payment details: " + response.getStatusCode());
        }
    }

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
