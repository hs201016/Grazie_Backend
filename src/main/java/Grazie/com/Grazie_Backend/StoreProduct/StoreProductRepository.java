package Grazie.com.Grazie_Backend.StoreProduct;

import Grazie.com.Grazie_Backend.Product.Product;
import Grazie.com.Grazie_Backend.Store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
    Chaean
    매장 상품 DB -> JPA
 */
public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {
    @Query("SELECT sp.product FROM StoreProduct sp WHERE sp.store.store_id = :storeId")
    List<Product>  findAllProductsByStoreId(@Param("storeId") Long storeId);

    // 중복 확인을 위한 메서드
    boolean existsByStoreAndProduct(Store store, Product product);

    @Query("SELECT sp FROM StoreProduct sp WHERE sp.store = :store AND sp.product.productId IN :productIds")
    List<StoreProduct> findByStoreAndProductIn(@Param("store") Store store, @Param("productIds") List<Long> productIds);

    List<StoreProduct> findAllByStore(Store store);

    void deleteByStore(Store store);

    void deleteByProduct(Product product);

    void deleteByStoreAndProduct(Store store, Product product);
}