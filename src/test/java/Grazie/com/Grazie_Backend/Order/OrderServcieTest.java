package Grazie.com.Grazie_Backend.Order;

import Grazie.com.Grazie_Backend.Order.OrderItems.OrderItemsDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
//@Transactional
@DisplayName("주문 서비스 테스트")
class OrderServcieTest {

    @Autowired
    private OrderServcie orderServcie;

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
        OrderDTO orderDTO = new OrderDTO();
        List<OrderItemsDTO> list = new ArrayList<>();

        orderDTO.setOrder_date(LocalDateTime.now());
        orderDTO.setOrder_mode("테이크아웃");
        orderDTO.setRequirement("얼음 빼주세요");
        orderDTO.setStore_id(1L);
        orderDTO.setCoupon_id(2L);
        orderDTO.setUser_id(5L);

        list.add(OrderItemsDTO.builder()
                .product_id(21L)
                .quantity(2)
                .product_price(4500)
                .build());

        list.add(OrderItemsDTO.builder()
                        .product_id(22L)
                        .quantity(3)
                        .product_price(3000)
                .build());

        list.add(OrderItemsDTO.builder()
                        .product_id(23L)
                        .quantity(1)
                        .product_price(6000)
                .build());


        Order order = orderServcie.createOrder(orderDTO, list);

        System.out.println(order.toString());
        System.out.println(order.getOrderItems());
    }

}