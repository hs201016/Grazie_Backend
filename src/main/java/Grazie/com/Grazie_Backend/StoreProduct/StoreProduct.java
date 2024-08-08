package Grazie.com.Grazie_Backend.StoreProduct;

import Grazie.com.Grazie_Backend.Product.Product;
import Grazie.com.Grazie_Backend.Store.Store;
import jakarta.persistence.*;

@Entity
@Table(name = "store_product")
// 복합키
@IdClass(StoreProductId.class)
public class StoreProduct {
    @Id
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
