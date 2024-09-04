package Grazie.com.Grazie_Backend.coupon;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
* 희수 : 추상클래스로 할인쿠폰과 상품쿠폰을 상속해주는 부모 클래스이기에 패키지위에 따로 뺴두었습니다
 */
@Entity
@Table(name = "coupon")  // 상속 계층의 루트 클래스에서 테이블 정의
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // SINGLE_TABLE 전략 사용
@DiscriminatorColumn(name = "coupon_type")  // 서브클래스를 구별하기 위한 열
@Getter
@Setter
public abstract class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String couponName;

    private String description;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false)
    private LocalDate issueDate;

    public Coupon() {
    }

    public Coupon(Long id, String couponName, String description, LocalDate expirationDate, LocalDate issueDate) {
        this.id = id;
        this.couponName = couponName;
        this.description = description;
        this.expirationDate = expirationDate;
        this.issueDate = issueDate;
    }
}
