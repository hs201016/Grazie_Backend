package Grazie.com.Grazie_Backend.Order;

import org.springframework.data.jpa.repository.JpaRepository;
/*
    Chaean00
    주문 DB -> JPA
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
