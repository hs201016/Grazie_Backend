package Grazie.com.Grazie_Backend.Pay.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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
    public void 주문조회() {
        String imp = "imp_042080014968";

        String response = payService.getPayDetails(imp);

        System.out.println(response);
    }

    @Test
    public void 결제취소() {
        String imp = "imp_566095829889";

        String response = payService.cancelPay(imp);

        System.out.println(response);
    }
}