package Grazie.com.Grazie_Backend.coupon.discountcoupon;


import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(summary = "할인 쿠폰을 생성합니다.", description = "새로운 할인 쿠폰을 생성합니다.")
    public ResponseEntity<DiscountCouponDTO> createDiscountCoupon(@RequestBody DiscountCouponDTO discountCouponDTO) {
        DiscountCouponDTO discountCoupon = discountCouponService.createDiscountCoupon(discountCouponDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(discountCoupon);
    }

    @GetMapping("/read/{id}")
    @Operation(summary = "특정 할인 쿠폰을 조회합니다.", description = "id를 통해 특정 할인 쿠폰을 조회합니다.")
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
    @Operation(summary = "특정 할인 쿠폰을 삭제합니다.", description = "id를 통해 특정 할인 쿠폰을 삭제합니다.")
    public ResponseEntity<Void> deleteDiscountCoupon(@PathVariable("id") Long id) {
        discountCouponService.deleteDiscountCoupon(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

