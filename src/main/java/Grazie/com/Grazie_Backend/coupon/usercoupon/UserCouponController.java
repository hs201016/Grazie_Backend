package Grazie.com.Grazie_Backend.coupon.usercoupon;

import Grazie.com.Grazie_Backend.coupon.Coupon;
import Grazie.com.Grazie_Backend.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class UserCouponController {

    private final UserCouponService userCouponService;
    private final CouponRepository couponRepository;

    @GetMapping("/list")
    public List<Coupon> getAvailableCoupons() {
        return couponRepository.findAll();
    }

    @PostMapping("/issue")
    public ResponseEntity<String> issueCoupon(@RequestParam("id") Long userId, @RequestBody UserCouponDTO couponDTO) {
        try {
            userCouponService.issueCoupon(userId, couponDTO.getCouponId(), couponDTO.getCouponType());
            return ResponseEntity.ok("쿠폰이 성공적으로 발급되었습니다!!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}



