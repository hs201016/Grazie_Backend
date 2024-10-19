package Grazie.com.Grazie_Backend.Order;

import Grazie.com.Grazie_Backend.Config.JwtUtil;
import Grazie.com.Grazie_Backend.Order.dto.OrderCreateDTO;
import Grazie.com.Grazie_Backend.Order.dto.OrderGetResponseDTO;
import Grazie.com.Grazie_Backend.Order.dto.OrderItemsCreateDTO;
import Grazie.com.Grazie_Backend.Order.dto.OrderSuccessDTO;
import Grazie.com.Grazie_Backend.Order.service.OrderService;
import Grazie.com.Grazie_Backend.coupon.CouponRepository;
import Grazie.com.Grazie_Backend.coupon.usercoupon.UserCouponRepository;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import Grazie.com.Grazie_Backend.personaloptions.enumfile.Concentration;
import Grazie.com.Grazie_Backend.personaloptions.enumfile.IceAddition;
import Grazie.com.Grazie_Backend.personaloptions.enumfile.TumblerUsage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@DisplayName("주문 서비스 테스트")
//@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    public void before() {
        System.out.println("테스트 시작");
        User user = userRepository.findById(2L).orElseThrow(() -> new RuntimeException("사용자 못찾음") );

        String token = jwtUtil.generateAccessToken(2L);
        Authentication authentication = jwtUtil.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    public void after() {
        System.out.println("테스트 끝");
    }

    @Test
    @DisplayName("주문 생성 테스트")
    public void createOrderTest() {
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
        List<OrderItemsCreateDTO> list = new ArrayList<>();

        orderCreateDTO.setOrder_date(LocalDateTime.now());
        orderCreateDTO.setOrder_mode("테이크아웃");
        orderCreateDTO.setRequirement("얼음 빼주세요");
        orderCreateDTO.setStore_id(2L);
        orderCreateDTO.setUser_id(2L);
//        orderCreateDTO.setCoupon_id(13L); // 아메리카노 1잔 무료 쿠폰

        list.add(OrderItemsCreateDTO.builder()
                .productId(21L)
                .productPrice(4000)
                .quantity(1)
                .size("medium")
                .temperature("ice")
                .concentration(Concentration.NORMAL)
                .personalTumbler(TumblerUsage.NOT_USE)
                .shotAddition(0)
                .pearlAddition(0)
                .syrupAddition(0)
                .whippedCreamAddition(1)
                .iceAddition(IceAddition.LESS)
                .build());

        list.add(OrderItemsCreateDTO.builder()
                .productId(2L)
                .productPrice(2300)
                .quantity(1)
                .size("small")
//                .couponId(13L)
                .temperature("ice")
                .concentration(Concentration.STRONG)
                .personalTumbler(TumblerUsage.NOT_USE)
                .shotAddition(2)
                .pearlAddition(0)
                .syrupAddition(0)
                .whippedCreamAddition(0)
                .iceAddition(IceAddition.MORE)
                .build());


        OrderSuccessDTO order = orderService.createOrder(orderCreateDTO, list);

        System.out.println(order.getOrderId().toString());
        System.out.println(order.getFinalPrice());
        System.out.println(order.getMessage());
    }

    @Test
    @DisplayName("주문 가져오기")
    public void getOrderByIdTest() {
        OrderGetResponseDTO DTO = orderService.getOrderById(3L);

        System.out.println(DTO.getOrderGetDTO().toString());
        System.out.println(DTO.getOrderItemsGetDTOs().toString());
    }

    @Test
    @DisplayName("모든 주문 가져오기")
    public void getAllOrderTest() {
        List<OrderGetResponseDTO> orderGetResponseDTOs = orderService.getAllOrder();

        for (OrderGetResponseDTO dto : orderGetResponseDTOs) {
            System.out.println(dto.getOrderGetDTO().toString());
            System.out.println(dto.getOrderItemsGetDTOs().toString());
            System.out.println();
        }
    }

    @Test
    @DisplayName("특정 매장의 모든 주문 가져오기")
    public void getOrderByStoreTest() {
        List<OrderGetResponseDTO> orderGetResponseDTOs = orderService.getOrderByStoreId(1L);

        for (OrderGetResponseDTO dto : orderGetResponseDTOs) {
            System.out.println(dto.getOrderGetDTO().toString());
            System.out.println(dto.getOrderItemsGetDTOs().toString());
            System.out.println();
        }
    }

    @Test
    @DisplayName("OrderId를 통한 주문 삭제")
    public void deleteOrderByIdTest() {
        Boolean flag = orderService.deleteOrderById(3L);

        System.out.println(flag);
    }

    @Test
    @DisplayName("OrderId를 통한 주문 상태 변경")
    public void updateOrderAcceptTest() {
        System.out.println(orderService.updateOrderAcceptById(16L, "완료"));
    }
}