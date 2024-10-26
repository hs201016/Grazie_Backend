package Grazie.com.Grazie_Backend.Pay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayRequestDTO {
    private String storeUid; // 상점 고유 ID
    private BigDecimal price; // 결제할 금액
    private String customerName; // 고객 이름
    private String customerEmail; // 고객 이메일
    private String customerTel; // 고객 연락처
    private String customerId; // 고객의 고유번호
    private List<Long> productId; // 구매할 상품의 고유번호
}
