package Grazie.com.Grazie_Backend.coupon.productcoupon;

import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.Product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCouponService {

    private final ProductRepository productRepository;

    private final ProductCouponRepository productCouponRepository;

    public ProductCouponDTO createProductCoupon(ProductCouponDTO productCouponDTO) {

        Product product = productRepository.findById(productCouponDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("상품이 없어요 id 확인해주세요"));

        ProductCoupon productCoupon = new ProductCoupon();
        productCoupon.setCouponName(productCouponDTO.getCouponName());
        productCoupon.setDescription(productCouponDTO.getDescription());
        productCoupon.setIssueDate(productCouponDTO.getIssueDate());
        productCoupon.setExpirationDate(productCouponDTO.getExpirationDate());
        productCoupon.setProduct(product); // Product를 설정

        productCoupon = productCouponRepository.save(productCoupon);

        return new ProductCouponDTO(productCoupon);
    }

    public ProductCouponDTO readProductCoupon(Long id) {
        ProductCoupon productCoupon = checkCouponId(id);
        return new ProductCouponDTO(productCoupon);
    }

    // 쿠폰 타입별로 조회하기
    public List<ProductCouponRequest> getCouponsByProductType() {
            List<ProductCoupon> productCoupons = productCouponRepository.findAll();

            return productCoupons.stream()
                    .map(coupon -> new ProductCouponRequest(
                            coupon.getId(),
                            coupon.getCouponName(),
                            coupon.getDescription(),
                            coupon.getProduct().getName(), // productName만 가져옴
                            coupon.getIssueDate(),
                            coupon.getExpirationDate()
                    ))
                    .collect(Collectors.toList());
        }


    public void deleteProductCoupon(Long id) {
        ProductCoupon productCoupon = checkCouponId(id);
        productCouponRepository.delete(productCoupon);
    }

    private ProductCoupon checkCouponId(Long id) {
        return productCouponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("상품 쿠폰이 없어요 상품 쿠폰 id 확인해주세요"));
    }

}
