package Grazie.com.Grazie_Backend.Product.repository;

import Grazie.com.Grazie_Backend.Product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
    Chaean
    상품 DB -> JPA
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    Integer countByName(String name);

    List<Product> findByProductIdIn(List<Long> productIds);
}
