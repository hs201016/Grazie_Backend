package Grazie.com.Grazie_Backend.coupon.usercoupon;

import Grazie.com.Grazie_Backend.coupon.Coupon;
import Grazie.com.Grazie_Backend.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    boolean existsByUserAndCoupon(User user, Coupon coupon);

    List<UserCoupon> findByUser(User user);

    Optional<UserCoupon> findByUserAndCoupon(User user, Coupon coupon);


}
