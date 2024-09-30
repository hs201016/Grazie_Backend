package Grazie.com.Grazie_Backend.Pay.service;

import Grazie.com.Grazie_Backend.Pay.dto.MessageDTO;
import Grazie.com.Grazie_Backend.Pay.dto.PayResponseDTO;
import Grazie.com.Grazie_Backend.Pay.entity.Pay;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@DisplayName("결제 서비스 테스트")
class PayServiceTest {


    private final PayService payService;

    @Autowired
    PayServiceTest(PayService payService) {
        this.payService = payService;
    }

    @BeforeEach
    public void before() {
        System.out.println("테스트 시작");
    }

    @AfterEach
    public void after() {
        System.out.println("테스트 끝");
    }

    @Test
    public void 엑세스토큰가져오기() {
        String token = payService.getAccessToken();
        System.out.println(token);
    }

    @Test
    public void 주문조회_성공() {
        String imp = "imp_042080014968"; // 정상 주문 (취소)
//        String imp = "imp_439868606559"; // 정상 주문
        PayResponseDTO response = payService.getPayDetails(imp);

        System.out.println(response.getCode());
        System.out.println(response.getMessage());
        System.out.println(response.getResponseDetails().toString());
    }

    @Test
    public void 주문조회_실패_존재하지않는ImpUid() {
        String imp = "imp_042080014168";

        PayResponseDTO response = payService.getPayDetails(imp);

        System.out.println(response.getCode());
        System.out.println(response.getMessage());
        System.out.println(response.getResponseDetails().toString());
    }

    @Test
    public void 결제취소() throws Exception {
        String imp = "imp_042080014968";

        PayResponseDTO response = payService.cancelPay(imp);

        System.out.println(response);
    }

    @Test
    public void 결제진행_성공() {
        String imp = "imp_439868606559";

        PayResponseDTO payResponseDTO = payService.processPay(imp);

        System.out.println(payResponseDTO.getCode());
        System.out.println(payResponseDTO.getMessage());
        System.out.println(payResponseDTO.getResponseDetails().toString());
    }

    @Test
    public void 결제취소진행_성공() {
        String imp = "imp_439868606559";

        MessageDTO messageDTO = payService.processCancelPay(imp);

        System.out.println(messageDTO.getCode());
        System.out.println(messageDTO.getMessage());
    }

    @Test
    public void 모든결제조회_성공() {
        String name = "테스트 주문자";

        List<Pay> payList = payService.getAllPayByBuyerName(name);

        System.out.println(payList.toString());
    }
}