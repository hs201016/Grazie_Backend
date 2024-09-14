package Grazie.com.Grazie_Backend.coupon.productcoupon;

import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.Product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCouponService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCouponRepository productCouponRepository;

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

    public void deleteProductCoupon(Long id) {
        ProductCoupon productCoupon = checkCouponId(id);
        productCouponRepository.delete(productCoupon);
    }

    private ProductCoupon checkCouponId(Long id) {
        return productCouponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("상품 쿠폰이 없어요 상품 쿠폰 id 확인해주세요"));
    }

}
