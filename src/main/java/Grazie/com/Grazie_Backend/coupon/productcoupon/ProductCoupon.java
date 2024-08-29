package Grazie.com.Grazie_Backend.coupon.productcoupon;

import Grazie.com.Grazie_Backend.Product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "Product_Coupon")
@Getter
@Setter
public class ProductCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String couponName;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Product 엔티티와 연관관계 설정

    public ProductCoupon() {
    }

    public ProductCoupon(Long id, String couponName, String description, LocalDate issueDate, LocalDate expirationDate, Product product) {
        this.id = id;
        this.couponName = couponName;
        this.description = description;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.product = product;
    }
}