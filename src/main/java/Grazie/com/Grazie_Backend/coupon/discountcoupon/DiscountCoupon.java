package Grazie.com.Grazie_Backend.coupon.discountcoupon;

import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.coupon.Coupon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("DISCOUNT")  // 서브클래스 구별 값 표시
@Getter
@Setter
public class DiscountCoupon extends Coupon {

    @Column(nullable = false)
    private java.math.BigDecimal discountRate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public DiscountCoupon() {
    }

    public DiscountCoupon(Long id, String couponName, String description, LocalDate expirationDate, LocalDate issueDate, BigDecimal discountRate, Product product) {
        super(id, couponName, description, expirationDate, issueDate);  // 부모 클래스의 생성자 호출
        this.discountRate = discountRate;
        this.product = product;
    }
}
