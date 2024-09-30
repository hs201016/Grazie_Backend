package Grazie.com.Grazie_Backend.coupon.productcoupon;

import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.coupon.Coupon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("PRODUCT")  // 서브클래스 구별 값
@Getter
@Setter
public class ProductCoupon extends Coupon {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public ProductCoupon() {
    }

    public ProductCoupon(Long id, String couponName, String description, LocalDate expirationDate, LocalDate issueDate, Product product) {
        super(id, couponName, description, expirationDate, issueDate);
        this.product = product;
    }
}