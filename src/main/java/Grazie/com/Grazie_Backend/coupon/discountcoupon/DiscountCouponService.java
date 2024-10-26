package Grazie.com.Grazie_Backend.coupon.discountcoupon;

import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.Product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountCouponService {

    private final DiscountCouponRepository discountRepository;

    private final ProductRepository productRepository;

    public DiscountCouponDTO createDiscountCoupon(DiscountCouponDTO discountCouponDTO) {
        Product product = productRepository.findById(discountCouponDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("상품이 없습니다"));

        DiscountCoupon discountCoupon = new DiscountCoupon();
        discountCoupon.setCouponName(discountCouponDTO.getCouponName());
        discountCoupon.setDescription(discountCouponDTO.getDescription());
        discountCoupon.setExpirationDate(discountCouponDTO.getExpirationDate());
        discountCoupon.setIssueDate(discountCouponDTO.getIssueDate());
        discountCoupon.setDiscountRate(discountCouponDTO.getDiscountRate());
        discountCoupon.setProduct(product);

        discountCoupon = discountRepository.save(discountCoupon);

        return new DiscountCouponDTO(discountCoupon);
    }

    public DiscountCouponDTO readDiscountCoupon(Long id) {
        DiscountCoupon discountCoupon = checkCouponId(id);
        return new DiscountCouponDTO(discountCoupon);
    }

    public void deleteDiscountCoupon(Long id) {
        DiscountCoupon discountCoupon = checkCouponId(id);
        discountRepository.delete(discountCoupon);
    }

    public List<DiscountCouponRequest> getCouponsByDiscountType() {
        List<DiscountCoupon> discountCoupons = discountRepository.findAll();

        return discountCoupons.stream()
                .map(coupon -> new DiscountCouponRequest(
                        coupon.getId(),
                        coupon.getCouponName(),
                        coupon.getDescription(),
                        coupon.getProduct().getName(), // productName만 가져옴
                        coupon.getIssueDate(),
                        coupon.getExpirationDate(),
                        coupon.getDiscountRate()
                ))
                .collect(Collectors.toList());
    }

    private DiscountCoupon checkCouponId(Long id) {
        return discountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("할인 쿠폰이 없어요 할인 쿠폰 id 확인해주세요"));
    }

}

