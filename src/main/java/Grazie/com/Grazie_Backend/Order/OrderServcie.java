package Grazie.com.Grazie_Backend.Order;

import Grazie.com.Grazie_Backend.Order.DTO.*;
import Grazie.com.Grazie_Backend.Order.OrderItems.OrderItems;
import Grazie.com.Grazie_Backend.Order.OrderItems.OrderItemsRepository;
import Grazie.com.Grazie_Backend.Product.Product;
import Grazie.com.Grazie_Backend.Product.ProductRepository;
import Grazie.com.Grazie_Backend.Store.Store;
import Grazie.com.Grazie_Backend.Store.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/*
    Chaean00
    주문 관련 Service
 */
@Service
@Slf4j
public class OrderServcie {

    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public OrderServcie(OrderRepository orderRepository, OrderItemsRepository orderItemsRepository, ProductRepository productRepository, StoreRepository storeRepository) {
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    // 주문 생성
    public Order createOrder(OrderCreateDTO orderCreateDTO, List<OrderItemsCreateDTO> orderItemsCreateDTOS) {
        int total = 0;
        Order order = new Order();
        List<OrderItems> orderItemsList = new ArrayList<>();

        Store store = storeRepository.findById(orderCreateDTO.getStore_id())
                .orElseThrow(() -> new EntityNotFoundException("매장을 찾을 수 없습니다."));

        for (OrderItemsCreateDTO orderItemsCreateDTO : orderItemsCreateDTOS) {
            OrderItems orderItems = new OrderItems();
            int price = orderItemsCreateDTO.getQuantity() * orderItemsCreateDTO.getProduct_price();
            Product product = productRepository.findById(orderItemsCreateDTO.getProduct_id())
                    .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

            orderItems.setProduct(product);
            orderItems.setQuantity(orderItemsCreateDTO.getQuantity());
            orderItems.setProduct_price(orderItemsCreateDTO.getProduct_price());
            orderItems.setTotal_price(price);
            orderItems.setOrder(order);

            total += price;
            orderItemsList.add(orderItems);
        }

        order.setStore(store);
        order.setId(orderCreateDTO.getUser_id());
        order.setCoupon_id(orderCreateDTO.getCoupon_id());
        order.setOrderItems(orderItemsList);
        order.setTotal_price(total);
        order.setDiscount_price(1000); // 추후에 수정해야함
        order.setFinal_price(total - 1000);
        order.setOrder_date(orderCreateDTO.getOrder_date());
        order.setOrder_mode(orderCreateDTO.getOrder_mode());
        order.setAccept("대기");
        order.setRequirement(orderCreateDTO.getRequirement());

        orderRepository.save(order);
        orderItemsRepository.saveAll(orderItemsList);

        return order;
//        User user = userRepository.findById(orderDTO.getUser_id());
    }

    // 주문 ID로 조회
    public OrderGetResponseDTO getOrderById(Long order_id) {
        Order order = orderRepository.findById(order_id).
                orElseThrow(() -> new EntityNotFoundException("주문번호를 찾을 수 없습니다"));

        List<OrderItems> orderItemsList = orderItemsRepository.findByOrder(order);

        OrderGetDTO orderGetDTO = OrderToOrderGetDTO(order);
        List<OrderItemsGetDTO> orderItemsGetDTO = OrderItemsToOrderItemsGetDTO(orderItemsList);

        return OrderGetResponseDTO
                .builder()
                .orderGetDTO(orderGetDTO)
                .orderItemsGetDTOs(orderItemsGetDTO)
                .build();
    }

    // 모든 주문 가져오기
    public List<OrderGetResponseDTO> getAllOrder() {
        List<OrderGetResponseDTO> orderGetResponseDTOs = new ArrayList<>();

        List<Order> orders = orderRepository.findAll();

        for (Order o : orders) {
            OrderGetDTO orderGetDTO = OrderToOrderGetDTO(o);

            List<OrderItemsGetDTO> orderItemsGetDTOs =
                    OrderItemsToOrderItemsGetDTO(orderItemsRepository.findByOrder(o));

            orderGetResponseDTOs.add(OrderGetResponseDTO
                    .builder()
                    .orderGetDTO(orderGetDTO)
                    .orderItemsGetDTOs(orderItemsGetDTOs)
                    .build());
        }

        return orderGetResponseDTOs;
    }

    private OrderGetDTO OrderToOrderGetDTO(Order order) {
        OrderGetDTO orderGetDTO = new OrderGetDTO();
        orderGetDTO.setOrder_id(order.getOrder_id());
        orderGetDTO.setId(order.getId());
        orderGetDTO.setStore(order.getStore());
        orderGetDTO.setCoupon_id(order.getCoupon_id());
        orderGetDTO.setTotal_price(order.getTotal_price());
        orderGetDTO.setDiscount_price(order.getDiscount_price());
        orderGetDTO.setFinal_price(order.getFinal_price());
        orderGetDTO.setOrder_date(order.getOrder_date());
        orderGetDTO.setOrder_mode(order.getOrder_mode());
        orderGetDTO.setAccept(order.getAccept());
        orderGetDTO.setRequirement(order.getRequirement());

        return orderGetDTO;
    }

    private List<OrderItemsGetDTO> OrderItemsToOrderItemsGetDTO(List<OrderItems> orderItemsList) {
        List<OrderItemsGetDTO> orderItemsGetDTO = new ArrayList<>();

        for (OrderItems item : orderItemsList) {
            OrderItemsGetDTO dto = new OrderItemsGetDTO();
            dto.setOrder_item_id(item.getOrder_item_id());
            dto.setProduct(item.getProduct());
            dto.setQuantity(item.getQuantity());
            dto.setProduct_price(item.getProduct_price());
            dto.setTotal_price(item.getTotal_price());

            orderItemsGetDTO.add(dto);
        }

        return orderItemsGetDTO;
    }

}
