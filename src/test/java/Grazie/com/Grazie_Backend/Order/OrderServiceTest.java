package Grazie.com.Grazie_Backend.Order;

import Grazie.com.Grazie_Backend.Order.dto.OrderCreateDTO;
import Grazie.com.Grazie_Backend.Order.dto.OrderGetResponseDTO;
import Grazie.com.Grazie_Backend.Order.dto.OrderItemsCreateDTO;
import Grazie.com.Grazie_Backend.Order.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@DisplayName("주문 서비스 테스트")
//@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @BeforeEach
    public void before() {
        System.out.println("테스트 시작");
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
        orderCreateDTO.setStore_id(1L);
        orderCreateDTO.setCoupon_id(2L);
        orderCreateDTO.setUser_id(5L);

        list.add(OrderItemsCreateDTO.builder()
                .product_id(21L)
                .quantity(2)
                .product_price(4500)
                .build());

        list.add(OrderItemsCreateDTO.builder()
                        .product_id(22L)
                        .quantity(3)
                        .product_price(3000)
                .build());

        list.add(OrderItemsCreateDTO.builder()
                        .product_id(23L)
                        .quantity(1)
                        .product_price(6000)
                .build());



        OrderGetResponseDTO order = orderService.createOrder(orderCreateDTO, list);

        System.out.println(order.getOrderGetDTO().toString());
        System.out.println(order.getOrderItemsGetDTOs().toString());
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