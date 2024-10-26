package Grazie.com.Grazie_Backend.Order.repository;

import Grazie.com.Grazie_Backend.Order.entity.Order;
import Grazie.com.Grazie_Backend.Store.entity.Store;
import Grazie.com.Grazie_Backend.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
    Chaean00
    주문 DB -> JPA
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStore(Store store);

    List<Order> findByUser(User user);
}
