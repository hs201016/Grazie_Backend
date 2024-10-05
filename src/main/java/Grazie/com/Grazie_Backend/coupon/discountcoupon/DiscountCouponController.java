package Grazie.com.Grazie_Backend.coupon.discountcoupon;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/discount-coupons")
public class DiscountCouponController {

    private final DiscountCouponService discountCouponService;
    private final DiscountCouponRepository discountCouponRepository;

    @PostMapping("/create")
    public ResponseEntity<DiscountCouponDTO> createDiscountCoupon(@RequestBody DiscountCouponDTO discountCouponDTO) {
        DiscountCouponDTO discountCoupon = discountCouponService.createDiscountCoupon(discountCouponDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(discountCoupon);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<DiscountCouponDTO> readDiscountCoupon(@PathVariable("id") Long id) {
        DiscountCouponDTO discountCouponDTO = discountCouponService.readDiscountCoupon(id);
        return ResponseEntity.ok(discountCouponDTO);
    }

    @GetMapping("/read")
    public ResponseEntity<?> readDiscountCoupon() {
        List<DiscountCouponRequest> couponsByDiscountType = discountCouponService.getCouponsByDiscountType();
        return ResponseEntity.ok().body(couponsByDiscountType);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDiscountCoupon(@PathVariable("id") Long id) {
        discountCouponService.deleteDiscountCoupon(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

