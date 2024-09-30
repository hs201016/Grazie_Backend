package Grazie.com.Grazie_Backend.coupon.discountcoupon;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discount-coupons")
public class DiscountCouponController {

    @Autowired
    DiscountCouponService discountCouponService;

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

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "특정 할인 쿠폰을 삭제합니다.", description = "id를 통해 특정 할인 쿠폰을 삭제합니다.")
    public ResponseEntity<Void> deleteDiscountCoupon(@PathVariable("id") Long id) {
        discountCouponService.deleteDiscountCoupon(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

