package Grazie.com.Grazie_Backend.Order.OrderItems;

import Grazie.com.Grazie_Backend.Order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
    Chaean00
    주문 상품 DB -> JPA
 */
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    List<OrderItems> findByOrder(Order order);
}
