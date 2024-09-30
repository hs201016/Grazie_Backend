package Grazie.com.Grazie_Backend.coupon.usercoupon;

import Grazie.com.Grazie_Backend.coupon.discountcoupon.DiscountCoupon;
import Grazie.com.Grazie_Backend.coupon.discountcoupon.DiscountCouponRepository;
import Grazie.com.Grazie_Backend.coupon.productcoupon.ProductCoupon;
import Grazie.com.Grazie_Backend.coupon.productcoupon.ProductCouponRepository;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserDiscountCouponServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCouponRepository userCouponRepository;

    @Mock
    private DiscountCouponRepository discountCouponRepository;

    @InjectMocks
    private UserCouponService userCouponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("유저에게 할인 쿠폰이 정상적으로 할당 되는지 테스트")
    void testIssueDiscountCoupon_Success() {
        // Given
        Long userId = 1L;
        Long couponId = 1L;
        String couponType = "DISCOUNT";

        User user = new User();
        user.setId(1L);

        DiscountCoupon discountCoupon = new DiscountCoupon();
        discountCoupon.setId(couponId);
        discountCoupon.setExpirationDate(LocalDate.now().plusDays(10));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(discountCouponRepository.findById(couponId)).thenReturn(Optional.of(discountCoupon));
        when(userCouponRepository.existsByUserAndCoupon(user, discountCoupon)).thenReturn(false); // 아직 발급 안된 상태


        userCouponService.issueCoupon(userId, couponId, couponType);

        verify(userCouponRepository).save(any(UserCoupon.class));
    }

    @Test
    @DisplayName("이미 할인쿠폰이 발급된 유저에게 다시 발급하려고 할 때 예외 발생")
    void testIssueDiscountCoupon_AlreadyIssued() {
        // Given
        Long userId = 1L;
        Long couponId = 1L;
        String couponType = "DISCOUNT";

        User user = new User();
        user.setId(1L);

        DiscountCoupon discountCoupon = new DiscountCoupon();
        discountCoupon.setId(couponId);
        discountCoupon.setExpirationDate(LocalDate.now().plusDays(10));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(discountCouponRepository.findById(couponId)).thenReturn(Optional.of(discountCoupon));
        when(userCouponRepository.existsByUserAndCoupon(user, discountCoupon)).thenReturn(true); // 이미 발급된 상태

        // When & Then
        assertThrows(IllegalStateException.class, () -> userCouponService.issueCoupon(userId, couponId, couponType));
    }

}