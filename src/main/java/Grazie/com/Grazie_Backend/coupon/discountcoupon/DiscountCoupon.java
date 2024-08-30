package Grazie.com.Grazie_Backend.coupon.discountcoupon;

import Grazie.com.Grazie_Backend.Product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "discount_coupon")
@Getter
@Setter
public class DiscountCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_name", nullable = false)
    private String couponName;

    @Column(name = "description")
    private String description;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "discount_rate", nullable = false)
    private java.math.BigDecimal discountRate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public DiscountCoupon() {
    }

    public DiscountCoupon(Long id, String couponName, String description, LocalDate expirationDate, LocalDate issueDate, BigDecimal discountRate, Product product) {
        this.id = id;
        this.couponName = couponName;
        this.description = description;
        this.expirationDate = expirationDate;
        this.issueDate = issueDate;
        this.discountRate = discountRate;
        this.product = product;
    }
}
