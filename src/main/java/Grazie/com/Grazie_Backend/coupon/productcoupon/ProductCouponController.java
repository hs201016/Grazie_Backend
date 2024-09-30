package Grazie.com.Grazie_Backend.coupon.productcoupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
public class ProductCouponController {


    @Autowired
    ProductCouponService productCouponService;

    @PostMapping("/create")
    public ResponseEntity<ProductCouponDTO> createProductCoupon(@RequestBody ProductCouponDTO productCouponDTO) {
        ProductCouponDTO productCoupon = productCouponService.createProductCoupon(productCouponDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productCoupon); // 응답 바디에 생성된 ProductDTO 포함
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ProductCouponDTO> readProductCouponDTO(@PathVariable("id") Long id) {
        ProductCouponDTO productCoupon = productCouponService.readProductCoupon(id);
        return ResponseEntity.ok(productCoupon);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductCouponDTO> deleteProductCoupon(@PathVariable("id") Long id) {
        productCouponService.deleteProductCoupon(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}