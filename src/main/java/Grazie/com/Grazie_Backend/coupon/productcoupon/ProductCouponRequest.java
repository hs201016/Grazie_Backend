package Grazie.com.Grazie_Backend.coupon.productcoupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 *쿠폰 조회할때 필요한 DTO
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCouponRequest {
    private Long couponId;
    private String couponName;
    private String description;
    private String productName;
    private LocalDate issueDate;
    private LocalDate expirationDate;
}
