package Grazie.com.Grazie_Backend.coupon.discountcoupon;

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
    public ResponseEntity<DiscountCouponDTO> createDiscountCoupon(@RequestBody DiscountCouponDTO discountCouponDTO) {
        DiscountCouponDTO discountCoupon = discountCouponService.createDiscountCoupon(discountCouponDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(discountCoupon);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<DiscountCouponDTO> readDiscountCoupon(@PathVariable("id") Long id) {
        DiscountCouponDTO discountCouponDTO = discountCouponService.readDiscountCoupon(id);
        return ResponseEntity.ok(discountCouponDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDiscountCoupon(@PathVariable("id") Long id) {
        discountCouponService.deleteDiscountCoupon(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

