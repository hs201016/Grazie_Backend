package Grazie.com.Grazie_Backend.Order;

import Grazie.com.Grazie_Backend.Order.OrderItems.OrderItems;
import Grazie.com.Grazie_Backend.Order.OrderItems.OrderItemsDTO;
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

    public Order createOrder(OrderDTO orderDTO, List<OrderItemsDTO> orderItemsDTOs) {
        int total = 0;
        Order order = new Order();
        List<OrderItems> orderItemsList = new ArrayList<>();

        Store store = storeRepository.findById(orderDTO.getStore_id())
                .orElseThrow(() -> new EntityNotFoundException("매장을 찾을 수 없습니다."));

        for (OrderItemsDTO orderItemsDTO : orderItemsDTOs) {
            OrderItems orderItems = new OrderItems();
            int price = orderItemsDTO.getQuantity() * orderItemsDTO.getProduct_price();
            Product product = productRepository.findById(orderItemsDTO.getProduct_id())
                    .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

            orderItems.setProduct(product);
            orderItems.setQuantity(orderItemsDTO.getQuantity());
            orderItems.setProduct_price(orderItemsDTO.getProduct_price());
            orderItems.setTotal_price(price);
            orderItems.setOrder(order);

            total += price;
            orderItemsList.add(orderItems);
        }

        order.setStore(store);
        order.setId(orderDTO.getUser_id());
        order.setCoupon_id(orderDTO.getCoupon_id());
        order.setOrderItems(orderItemsList);
        order.setTotal_price(total);
        order.setDiscount_price(1000); // 추후에 수정해야함
        order.setFinal_price(total - 1000);
        order.setOrder_date(orderDTO.getOrder_date());
        order.setOrder_mode(orderDTO.getOrder_mode());
        order.setAccept("대기");
        order.setRequirement(orderDTO.getRequirement());

        orderRepository.save(order);
        orderItemsRepository.saveAll(orderItemsList);

        return order;
//        User user = userRepository.findById(orderDTO.getUser_id());
    }
}
