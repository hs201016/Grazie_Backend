package Grazie.com.Grazie_Backend.coupon.usercoupon;

import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.coupon.Coupon;
import Grazie.com.Grazie_Backend.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class UserCouponController {

    private final UserCouponService userCouponService;
    private final CouponRepository couponRepository;


    @GetMapping("/list")
    public ResponseEntity<List<Coupon>> getAvailableCoupons(@AuthenticationPrincipal UserAdapter userAdapter) {
        Long userId = getUserIdFromUserDetails(userAdapter);
        List<Coupon> availableCoupon = userCouponService.getAvailableCoupon(userId);
        return ResponseEntity.ok(availableCoupon);
    }

    @PostMapping("/issue")
    public ResponseEntity<String> issueCoupon(@AuthenticationPrincipal UserAdapter userAdapter, @RequestBody UserCouponDTO couponDTO) {
        Long userId = getUserIdFromUserDetails(userAdapter); // 사용자 ID 추출

        try {
            userCouponService.issueCoupon(userId, couponDTO.getCouponId(), couponDTO.getCouponType());
            return ResponseEntity.ok("쿠폰이 성공적으로 발급되었습니다!!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Long getUserIdFromUserDetails(UserAdapter userAdapter) {
        return userAdapter.getUser().getId();
    }
}



