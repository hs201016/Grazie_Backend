package Grazie.com.Grazie_Backend.StoreProduct.repository;

import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.Store.entity.Store;
import Grazie.com.Grazie_Backend.StoreProduct.entity.StoreProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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
    @Query("SELECT sp FROM StoreProduct sp WHERE sp.store.store_id = :storeId AND sp.product.productId = :productId")
    Optional<StoreProduct> findByStoreIdAndProductId(@Param("storeId") Long storeId, @Param("productId") Long productId);

    void deleteByStore(Store store);

    void deleteByProduct(Product product);

    void deleteByStoreAndProduct(Store store, Product product);
}
