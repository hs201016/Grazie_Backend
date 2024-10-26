package Grazie.com.Grazie_Backend.coupon.discountcoupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 *쿠폰 조회할때 필요한 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCouponRequest {
    private Long couponId;
    private String couponName;
    private String description;
    private String productName;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private BigDecimal discountRate;
}
