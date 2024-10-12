package Grazie.com.Grazie_Backend.Product.repository;

import Grazie.com.Grazie_Backend.Product.dto.ProductDistinctDTO;
import Grazie.com.Grazie_Backend.Product.dto.ProductSizeTempDTO;
import Grazie.com.Grazie_Backend.Product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
    Chaean
    상품 DB -> JPA
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Integer countByName(String name);

    List<Product> findByProductIdIn(List<Long> productIds);

    @Query("SELECT new Grazie.com.Grazie_Backend.Product.dto.ProductDistinctDTO(" +
            "p.name, p.image, MIN(p.price), p.explanation, p.information, p.allergy, p.category) " +
            "FROM Product p " +
            "WHERE (p.category = 'Hot Coffee' AND p.temperature = 'hot') OR " +
            "(p.category = 'Ice Coffee' AND p.temperature = 'ice') OR " +
            "(p.category NOT IN ('Hot Coffee', 'Ice Coffee')) " +
            "GROUP BY p.name, p.image, p.explanation, p.information, p.allergy, p.category " +
            "ORDER BY MIN(p.productId)")
    List<ProductDistinctDTO> findDistinctProducts();

    @Query("SELECT new Grazie.com.Grazie_Backend.Product.dto.ProductSizeTempDTO(p.productId, p.size, p.temperature) " +
            "FROM Product p " +
            "WHERE p.name = :name")
    List<ProductSizeTempDTO> findProductSizeTempByName(@Param("name") String name);

}
