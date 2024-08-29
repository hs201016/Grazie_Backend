package Grazie.com.Grazie_Backend.Order;

import Grazie.com.Grazie_Backend.Order.DTO.*;
import Grazie.com.Grazie_Backend.Order.OrderItems.OrderItems;
import Grazie.com.Grazie_Backend.Order.OrderItems.OrderItemsRepository;
import Grazie.com.Grazie_Backend.Product.Product;
import Grazie.com.Grazie_Backend.Product.ProductRepository;
import Grazie.com.Grazie_Backend.Store.Store;
import Grazie.com.Grazie_Backend.Store.StoreRepository;
import Grazie.com.Grazie_Backend.StoreProduct.StoreProduct;
import Grazie.com.Grazie_Backend.StoreProduct.StoreProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Chaean00
    주문 관련 Service
 */
@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final StoreProductRepository storeProductRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemsRepository orderItemsRepository, ProductRepository productRepository, StoreRepository storeRepository, StoreProductRepository storeProductRepository) {
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.storeProductRepository = storeProductRepository;
    }

    // 주문 생성
    @Transactional
    public OrderGetResponseDTO createOrder(OrderCreateDTO orderCreateDTO, List<OrderItemsCreateDTO> orderItemsCreateDTOS) {
        int total = 0;

        Store store = storeRepository.findById(orderCreateDTO.getStore_id())
                .orElseThrow(() -> new EntityNotFoundException("매장을 찾을 수 없습니다."));

        Order order = new Order();

        List<Long> productIds = orderItemsCreateDTOS.stream()
                .map(OrderItemsCreateDTO::getProduct_id)
                .toList();

        List<Product> products = productRepository.findByProductIdIn(productIds);
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductId, product -> product));

        List<StoreProduct> storeProducts = storeProductRepository.findByStoreAndProductIn(store, productIds);
        Map<Long, StoreProduct> storeProductMap = storeProducts.stream()
                .collect(Collectors.toMap(sp -> sp.getProduct().getProductId(), sp -> sp));

        List<OrderItems> orderItemsList = new ArrayList<>();

        for (OrderItemsCreateDTO orderItemsCreateDTO : orderItemsCreateDTOS) {
            Product product = productMap.get(orderItemsCreateDTO.getProduct_id());
            if (product == null) {
                throw new EntityNotFoundException("상품을 찾을 수 없습니다.");
            }

            StoreProduct storeProduct = storeProductMap.get(product.getProductId());
            if (storeProduct == null) {
                throw new IllegalArgumentException("매장에서 판매하지 않는 상품입니다.");
            }

            if (!storeProduct.getState()) {
                throw new IllegalArgumentException("판매 중지 된 상품입니다.");
            }

            int price = orderItemsCreateDTO.getQuantity() * orderItemsCreateDTO.getProduct_price();

            OrderItems orderItems = new OrderItems();
            orderItems.setProduct(product);
            orderItems.setQuantity(orderItemsCreateDTO.getQuantity());
            orderItems.setProduct_price(orderItemsCreateDTO.getProduct_price());
            orderItems.setTotal_price(price);
            orderItems.setOrder(order);

            total += price;
            orderItemsList.add(orderItems);
        }

        // Order 생성 및 저장

        order.setStore(store);
        order.setId(orderCreateDTO.getUser_id());
        order.setCoupon_id(orderCreateDTO.getCoupon_id());
        order.setOrderItems(orderItemsList);
        order.setTotal_price(total);
        order.setDiscount_price(1000); // 추후 수정 필요
        order.setFinal_price(total - 1000);
        order.setOrder_date(orderCreateDTO.getOrder_date());
        order.setOrder_mode(orderCreateDTO.getOrder_mode());
        order.setAccept("대기");
        order.setRequirement(orderCreateDTO.getRequirement());

        // Order와 OrderItems를 함께 저장
        try {
            orderRepository.save(order);
            orderItemsRepository.saveAll(orderItemsList);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("데이터 무결성 위반 오류 발생 ", e);
        }

        OrderGetDTO orderGetDTO = OrderToOrderGetDTO(order);
        List<OrderItemsGetDTO> orderItemsGetDTO = OrderItemsToOrderItemsGetDTO(orderItemsList);

        return OrderGetResponseDTO
                .builder()
                .orderGetDTO(orderGetDTO)
                .orderItemsGetDTOs(orderItemsGetDTO)
                .build();
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

    // 특정 매장의 모든 주문 가져오기
    public List<OrderGetResponseDTO> getOrderByStoreId(Long store_id) {
        Store store = storeRepository.findById(store_id)
                .orElseThrow(() -> new EntityNotFoundException("매장을 찾을 수 없습니다."));

        List<Order> orders = orderRepository.findByStore(store);

        List<OrderGetResponseDTO> orderGetResponseDTOs = new ArrayList<>();

        for (Order o : orders) {
            OrderGetDTO orderGetDTO = OrderToOrderGetDTO(o);
            orderGetDTO.setStore(null);

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

    // 주문 취소(삭제)
    public boolean deleteOrderById(Long order_id) {
        Order order = orderRepository.findById(order_id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지않는 주문 번호입니다."));
        orderRepository.deleteById(order_id);
        return true;
    }

    // 주문 상태 변경하기
    public String updateOrderAcceptById(Long order_id, String state) {

        if (state.equals("대기") || state.equals("준비중") || state.equals("완료") || state.equals("픽업완료") || state.equals("배달완료")) {
            Order order = orderRepository.findById(order_id)
                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 주문입니다."));

            order.setAccept(state);

            orderRepository.save(order);

            return order.getAccept();
        } else {
            throw new IllegalArgumentException("잘못된 문자열입니다.");
        }
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
