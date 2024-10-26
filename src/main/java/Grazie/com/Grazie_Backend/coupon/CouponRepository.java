package Grazie.com.Grazie_Backend.coupon;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 최상위 쿠폰 리포지토리
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
