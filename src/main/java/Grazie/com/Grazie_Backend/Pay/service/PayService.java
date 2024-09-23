package Grazie.com.Grazie_Backend.Pay.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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

    private String testImp = "imp_044678610251";

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

    // imp를 이용한
    public String getPayDetails(String imp, String accessToken) {
        String url = impBaseUrl + "/payments/" + imp;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

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
