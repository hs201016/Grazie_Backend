package Grazie.com.Grazie_Backend.cart.repository;

import Grazie.com.Grazie_Backend.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartId(Long cartId);

}
