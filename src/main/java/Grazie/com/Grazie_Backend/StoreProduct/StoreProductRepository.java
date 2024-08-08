package Grazie.com.Grazie_Backend.StoreProduct;

import Grazie.com.Grazie_Backend.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {
    @Query("SELECT sp.product FROM StoreProduct sp WHERE sp.store.store_id = :storeId")
    List<Product>  findAllProductsByStoreId(@Param("storeId") Long storeId);
}
