package Grazie.com.Grazie_Backend.coupon.discountcoupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DiscountCouponDTO {
    private Long id;
    private String couponName;
    private String description;
    private LocalDate expirationDate;
    private LocalDate issueDate;
    private BigDecimal discountRate;
    private Long productId;

    public DiscountCouponDTO(DiscountCoupon discountCoupon) {
        this.id = discountCoupon.getId();
        this.couponName = discountCoupon.getCouponName();
        this.description = discountCoupon.getDescription();
        this.expirationDate = discountCoupon.getExpirationDate();
        this.issueDate = discountCoupon.getIssueDate();
        this.discountRate = discountCoupon.getDiscountRate();
        this.productId = discountCoupon.getProduct().getProductId(); // Ensure the product ID is correctly set
    }


}
