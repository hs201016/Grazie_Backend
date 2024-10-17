package Grazie.com.Grazie_Backend.Order.service;

import Grazie.com.Grazie_Backend.Order.exception.*;
import Grazie.com.Grazie_Backend.Order.repository.OrderRepository;
import Grazie.com.Grazie_Backend.Order.dto.*;
import Grazie.com.Grazie_Backend.Order.OrderItems.entity.OrderItems;
import Grazie.com.Grazie_Backend.Order.OrderItems.repository.OrderItemsRepository;
import Grazie.com.Grazie_Backend.Order.entity.Order;
import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.Product.repository.ProductRepository;
import Grazie.com.Grazie_Backend.Store.entity.Store;
import Grazie.com.Grazie_Backend.Store.repository.StoreRepository;
import Grazie.com.Grazie_Backend.StoreProduct.entity.StoreProduct;
import Grazie.com.Grazie_Backend.StoreProduct.repository.StoreProductRepository;
import Grazie.com.Grazie_Backend.cart.service.CartService;
import Grazie.com.Grazie_Backend.coupon.Coupon;
import Grazie.com.Grazie_Backend.coupon.CouponRepository;
import Grazie.com.Grazie_Backend.coupon.discountcoupon.DiscountCoupon;
import Grazie.com.Grazie_Backend.coupon.productcoupon.ProductCoupon;
import Grazie.com.Grazie_Backend.coupon.usercoupon.UserCouponRepository;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    private final UserCouponRepository userCouponRepository;
    private final CartService cartService;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemsRepository orderItemsRepository, ProductRepository productRepository, StoreRepository storeRepository, StoreProductRepository storeProductRepository, UserRepository userRepository, CouponRepository couponRepository, UserCouponRepository userCouponRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.storeProductRepository = storeProductRepository;
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
        this.userCouponRepository = userCouponRepository;
        this.cartService = cartService;
    }

    // 주문 생성
    @Transactional
    public OrderSuccessDTO createOrder(OrderCreateDTO orderCreateDTO, List<OrderItemsCreateDTO> orderItemsCreateDTOS) {
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal discountPrice = BigDecimal.ZERO;

        Store store = storeRepository.findById(orderCreateDTO.getStore_id())
                .orElseThrow(() -> new StoreNotFoundException("매장을 찾을 수 없습니다."));

        User user = userRepository.findById(orderCreateDTO.getUser_id())
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));

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

            BigDecimal price = BigDecimal.ZERO;

            if (product == null) {
                throw new ProductNotFoundException("상품을 찾을 수 없습니다.");
            }

            StoreProduct storeProduct = storeProductMap.get(product.getProductId());
            if (storeProduct == null) {
                throw new ProductNotSoldException("매장에서 판매하지 않는 상품입니다.");
            }

            if (!storeProduct.getState()) {
                throw new ProductDiscontinuedException("판매 중지 된 상품입니다.");
            }

            // 상품 개수 * 상품 가격
            price = new BigDecimal(orderItemsCreateDTO.getQuantity()).multiply(new BigDecimal(orderItemsCreateDTO.getProduct_price()));

            OrderItems orderItems = new OrderItems();
            orderItems.setProduct_price(orderItemsCreateDTO.getProduct_price());
            orderItems.setQuantity(orderItemsCreateDTO.getQuantity());
            orderItems.setTotal_price(price.intValue());
            orderItems.setSize(orderItemsCreateDTO.getSize());
            orderItems.setTemperature(orderItemsCreateDTO.getTemperature());
            // personal option
            orderItems.setShotAddition(orderItemsCreateDTO.getShotAddition());
            orderItems.setPersonalTumbler(orderItemsCreateDTO.getPersonalTumbler());
            orderItems.setPearlAddition(orderItemsCreateDTO.getPearlAddition());
            orderItems.setSyrupAddition(orderItemsCreateDTO.getSyrupAddition());
            orderItems.setWhippedCreamAddition(orderItemsCreateDTO.getWhippedCreamAddition());
            orderItems.setIceAddition(orderItemsCreateDTO.getIceAddition());

            orderItems.setOrder(order);
            orderItems.setProduct(product);


            total = total.add(price);
            orderItemsList.add(orderItems);
        }

        // 쿠폰 처리 로직
        Coupon coupon = null;
        if (orderCreateDTO.getCoupon_id() != null) {
            coupon = couponRepository.findById(orderCreateDTO.getCoupon_id())
                    .orElseThrow(() -> new CouponNotFoundException("쿠폰을 찾을 수 없습니다."));

            if (coupon instanceof DiscountCoupon) {
                DiscountCoupon discountCoupon = (DiscountCoupon) coupon;
                for (OrderItems orderItems : orderItemsList) {
                    if (orderItems.getProduct().equals(discountCoupon.getProduct())) {
                        if (discountCoupon.getExpirationDate().isBefore(LocalDate.now())) {
                            throw new CouponExpiredException("기한이 만료된 쿠폰입니다.");
                        } else {
                            /*
                                 user가 가지고있는 쿠폰인지 체크 필요
                             */
                            if (userCouponRepository.existsByUserAndCoupon(user, discountCoupon)) {
                                BigDecimal productPrice = new BigDecimal(orderItems.getProduct_price());
                                BigDecimal discountRate = discountCoupon.getDiscountRate().divide(BigDecimal.valueOf(100));
                                discountPrice = productPrice.multiply(discountRate);
                                orderItems.setTotal_price(new BigDecimal(orderItems.getTotal_price()).subtract(discountPrice).intValue());
                            } else {
                                throw new CouponNotOwnedException("유저가 해당 쿠폰을 가지고 있지 않습니다.");
                            }
                        }
                    }
                }
            } else if (coupon instanceof ProductCoupon) {
                ProductCoupon productCoupon = (ProductCoupon) coupon;
                for (OrderItems orderItems : orderItemsList) {
                    if (orderItems.getProduct().equals(productCoupon.getProduct())) {
                        if (productCoupon.getExpirationDate().isBefore(LocalDate.now())) {
                            throw new CouponExpiredException("기한이 만료된 쿠폰입니다.");
                        } else {
                            /*
                                 user가 가지고있는 쿠폰인지 체크 필요
                             */
                            if (userCouponRepository.existsByUserAndCoupon(user, productCoupon)) {
                                discountPrice = discountPrice.add(new BigDecimal(orderItems.getProduct_price()));
                                orderItems.setTotal_price(new BigDecimal(orderItems.getTotal_price()).subtract(discountPrice).intValue());
                            } else {
                                throw new CouponNotOwnedException("유저가 해당 쿠폰을 가지고 있지 않습니다.");
                            }
                        }
                    }
                }
            }
        }

        // Order 생성 및 저장
        order.setTotal_price(total.intValue());
        order.setDiscount_price(discountPrice.intValue());
        order.setFinal_price(total.subtract(discountPrice).intValue());
        order.setOrder_date(orderCreateDTO.getOrder_date());
        order.setOrder_mode(orderCreateDTO.getOrder_mode());
        order.setCup_type(orderCreateDTO.getCup_type());
        order.setAccept("대기");
        order.setRequirement(orderCreateDTO.getRequirement());
        order.setStore(store);
        order.setUser(user);
        order.setCoupon_id(coupon);
        order.setOrderItems(orderItemsList);

        // Order와 OrderItems를 함께 저장
        try {
            orderRepository.save(order);
            orderItemsRepository.saveAll(orderItemsList);
            // 주문이 성공적으로 끝나면 장바구니 비우기
            cartService.deleteAllCartItems(orderCreateDTO.getUser_id());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("데이터 무결성 위반 오류 발생 ", e);
        }

//        OrderGetDTO orderGetDTO = OrderToOrderGetDTO(order);
//        List<OrderItemsGetDTO> orderItemsGetDTO = OrderItemsToOrderItemsGetDTO(orderItemsList);

        return OrderSuccessDTO
                .builder()
                .orderId(order.getOrder_id())
                .finalPrice(total.subtract(discountPrice).intValue())
                .message("주문이 성공적으로 접수되었습니다.")
                .build();
    }

    // 주문 ID로 조회
    public OrderGetResponseDTO getOrderById(Long order_id) {
        Order order = orderRepository.findById(order_id).
                orElseThrow(() -> new OrderNotFoundException("존재하지 않는 주문 번호입니다."));

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
                .orElseThrow(() -> new StoreNotFoundException("매장을 찾을 수 없습니다."));

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
                .orElseThrow(() -> new OrderNotFoundException("존재하지않는 주문 번호입니다."));
        orderRepository.deleteById(order_id);
        return true;
    }

    // 주문 상태 변경하기
    public String updateOrderAcceptById(Long order_id, String state) {

        if (state.equals("대기") || state.equals("준비중") || state.equals("완료") || state.equals("픽업완료") || state.equals("배달완료")) {
            Order order = orderRepository.findById(order_id)
                    .orElseThrow(() -> new OrderNotFoundException("존재하지 않는 주문입니다."));

            order.setAccept(state);

            orderRepository.save(order);

            return order.getAccept();
        } else {
            throw new IllegalArgumentException("잘못된 문자열입니다.");
        }
    }

    // 유저의 모든 주문 조회
    public List<OrderGetResponseDTO> getOrderByUserId(Long user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new UserNotFoundException("존재하지않는 유저입니다."));

        List<Order> orders = orderRepository.findByUser(user);
        List<OrderGetResponseDTO> orderGetResponseDTOs = new ArrayList<>();

        for (Order o : orders) {
            OrderGetDTO orderGetDTO = OrderToOrderGetDTO(o);
            orderGetDTO.setUser_id(null);

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
        orderGetDTO.setUser_id(order.getUser());
        orderGetDTO.setStore(order.getStore());
        orderGetDTO.setCoupon_id(order.getCoupon_id());
        orderGetDTO.setTotal_price(order.getTotal_price());
        orderGetDTO.setDiscount_price(order.getDiscount_price());
        orderGetDTO.setFinal_price(order.getFinal_price());
        orderGetDTO.setOrder_date(order.getOrder_date());
        orderGetDTO.setOrder_mode(order.getOrder_mode());
        orderGetDTO.setAccept(order.getAccept());
        orderGetDTO.setCup_type(order.getCup_type());
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
            dto.setSize(item.getSize());
            dto.setProduct_price(item.getProduct_price());
            dto.setTotal_price(item.getTotal_price());

            orderItemsGetDTO.add(dto);
        }

        return orderItemsGetDTO;
    }
}
