package Grazie.com.Grazie_Backend.Product;

import org.springframework.data.jpa.repository.JpaRepository;
/*
    Chaean
    상품 DB -> JPA
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    Integer countByName(String name);
}
