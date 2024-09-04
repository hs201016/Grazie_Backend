package Grazie.com.Grazie_Backend.coupon.usercoupon;

import Grazie.com.Grazie_Backend.coupon.Coupon;
import Grazie.com.Grazie_Backend.member.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "user_coupon")
@Getter
@Setter
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    private Boolean isUsed = false;

    private LocalDate issuedAt;

    @Column(nullable = false)
    private LocalDate expirationDate;

    public UserCoupon() {
    }
    public UserCoupon(User user, Coupon coupon, Boolean isUsed, LocalDate issuedAt, LocalDate expirationDate) {
        this.user = user;
        this.coupon = coupon;
        this.isUsed = isUsed;
        this.issuedAt = issuedAt;
        this.expirationDate = expirationDate;
    }
}
