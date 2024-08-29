package Grazie.com.Grazie_Backend.Order;

import Grazie.com.Grazie_Backend.Store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
    Chaean00
    주문 DB -> JPA
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStore(Store store);
}
