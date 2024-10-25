package Grazie.com.Grazie_Backend.coupon.usercoupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserCouponResponse {
    private String couponName;
    private String description;
    private LocalDate expirationDate;
    private LocalDate issueDate;
}
