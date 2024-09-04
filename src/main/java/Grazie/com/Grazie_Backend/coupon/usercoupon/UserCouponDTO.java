package Grazie.com.Grazie_Backend.coupon.usercoupon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCouponDTO {
    private Long couponId;
    private String couponType; // "DISCOUNT" 또는 "PRODUCT"
}



