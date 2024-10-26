package Grazie.com.Grazie_Backend.Order.OrderItems.repository;

import Grazie.com.Grazie_Backend.Order.entity.Order;
import Grazie.com.Grazie_Backend.Order.OrderItems.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
    Chaean00
    주문 상품 DB -> JPA
 */
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findByOrder(Order order);
}
