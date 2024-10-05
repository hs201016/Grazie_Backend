package Grazie.com.Grazie_Backend.coupon.productcoupon;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductCouponDTO {
    private String couponName;
    private String description;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private Long productId;

    public ProductCouponDTO(ProductCoupon productCoupon) {
        this.couponName = productCoupon.getCouponName();
        this.description = productCoupon.getDescription();
        this.issueDate = productCoupon.getIssueDate();
        this.expirationDate = productCoupon.getExpirationDate();
        this.productId = productCoupon.getProduct().getProductId(); // productId를 포함
    }

    public ProductCouponDTO() {
    }
}
