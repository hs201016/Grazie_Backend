package Grazie.com.Grazie_Backend.coupon.usercoupon;

import Grazie.com.Grazie_Backend.Config.SecurityUtils;
import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.coupon.Coupon;
import Grazie.com.Grazie_Backend.coupon.CouponRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class UserCouponController {

    private final UserCouponService userCouponService;
    private final CouponRepository couponRepository;


    @GetMapping("/list")
    @Operation(summary = "유저가 발급받지 않은 쿠폰 리스트를 조회합니다.", description = "유저가 발급받지 않은 쿠폰 리스트를 조회합니다.")
    public ResponseEntity<List<Coupon>> getAvailableCoupons() {
        SecurityUtils.getCurrentUser();
        List<Coupon> availableCoupon = userCouponService.getAvailableCoupon();
        return ResponseEntity.ok(availableCoupon);
    }

    @GetMapping("/issued-list")
    @Operation(summary = "유저가 발급받은 쿠폰 리스트를 조회합니다.", description = "유저가 발급받은 쿠폰 리스트를 조회합니다.")
    public ResponseEntity<List<UserCouponResponse>> getIssuedCoupons() {
        SecurityUtils.getCurrentUser();
        List<UserCouponResponse> issueCouponList = userCouponService.getIssueCouponList();
        return ResponseEntity.ok(issueCouponList);
    }

    @PostMapping("/issue")
    @Operation(summary = "유저에게 쿠폰을 발급합니다.", description = "유저에게 새로운 쿠폰을 발급합니다.")
    public ResponseEntity<String> issueCoupon(@RequestBody UserCouponRequest couponDTO) {
        Long userId = getUserIdFromUserDetails();
        try {
            userCouponService.issueCoupon(userId, couponDTO.getCouponId(), couponDTO.getCouponType());
            return ResponseEntity.ok("쿠폰이 성공적으로 발급되었습니다!!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Long getUserIdFromUserDetails() {
        UserAdapter currentUser = SecurityUtils.getCurrentUser();
        Long userId = currentUser.getUserId();
        return userId;
    }
}



