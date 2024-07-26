package Grazie.com.Grazie_Backend.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Integer countByName(String name);
}
