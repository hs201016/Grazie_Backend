package Grazie.com.Grazie_Backend.coupon.discount;

import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.Product.repository.ProductRepository;
import Grazie.com.Grazie_Backend.coupon.discountcoupon.DiscountCoupon;
import Grazie.com.Grazie_Backend.coupon.discountcoupon.DiscountCouponDTO;
import Grazie.com.Grazie_Backend.coupon.discountcoupon.DiscountCouponRepository;
import Grazie.com.Grazie_Backend.coupon.discountcoupon.DiscountCouponService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DiscountCouponServiceTest {

    @Mock
    private DiscountCouponRepository discountRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DiscountCouponService discountCouponService;

    private DiscountCoupon discountCoupon;
    private DiscountCouponDTO discountCouponDTO;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setProductId(1L);
        product.setName("테스트 상품");

        discountCoupon = new DiscountCoupon(1L, "테스트 Sale 쿠폰", "50% off", LocalDate.now().plusDays(30), LocalDate.now(), new BigDecimal("0.50"), product);

        discountCouponDTO = new DiscountCouponDTO();
        discountCouponDTO.setCouponName("테스트 Sale 쿠폰");
        discountCouponDTO.setDescription("50% off");
        discountCouponDTO.setExpirationDate(LocalDate.now().plusDays(30));
        discountCouponDTO.setIssueDate(LocalDate.now());
        discountCouponDTO.setDiscountRate(new BigDecimal("0.50"));
        discountCouponDTO.setProductId(1L);
    }

    @Test
    @DisplayName("할인 쿠폰 생성 테스트")
    public void testCreateDiscountCoupon() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(discountRepository.save(any(DiscountCoupon.class))).thenReturn(discountCoupon);

        DiscountCouponDTO createdCoupon = discountCouponService.createDiscountCoupon(discountCouponDTO);

        assertNotNull(createdCoupon);
        assertEquals("테스트 Sale 쿠폰", createdCoupon.getCouponName());
        assertEquals("50% off", createdCoupon.getDescription());

        verify(discountRepository, times(1)).save(any(DiscountCoupon.class));
    }

    @Test
    @DisplayName("할인 쿠폰 조회 테스트")
    public void testReadDiscountCoupon() {
        when(discountRepository.findById(1L)).thenReturn(Optional.of(discountCoupon));

        DiscountCouponDTO discountCouponDTO1 = discountCouponService.readDiscountCoupon(1L);

        assertNotNull(discountCouponDTO1);
        assertEquals("테스트 Sale 쿠폰", discountCouponDTO1.getCouponName()); // 쿠폰이름 확인
        verify(discountRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("할인 쿠폰 삭제 테스트")
    public void testDeleteDiscountCoupon() {
        when(discountRepository.findById(1L)).thenReturn(Optional.of(discountCoupon));

        discountCouponService.deleteDiscountCoupon(1L);

        verify(discountRepository, times(1)).delete(discountCoupon);
    }

    @Test
    @DisplayName("잘못된 할인 쿠폰 조회시 예외 테스트")
    public void testReadDiscountCouponTest() {
        when(discountRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> discountCouponService.readDiscountCoupon(1L));
    }

    @Test
    @DisplayName("잘못된 할인 쿠폰 삭제시 예외 테스트")
    public void testDeleteDiscountCouponNotFound() {
        when(discountRepository.findById(1L)).thenReturn(Optional.empty());
            assertThrows(EntityNotFoundException.class, () -> discountCouponService.deleteDiscountCoupon(1L));
        }
    }



