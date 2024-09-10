package Grazie.com.Grazie_Backend.cart.repository;

import Grazie.com.Grazie_Backend.cart.entity.Cart;
import Grazie.com.Grazie_Backend.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

}
