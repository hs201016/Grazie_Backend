package Grazie.com.Grazie_Backend.StoreProduct;

import Grazie.com.Grazie_Backend.Product.Product;
import Grazie.com.Grazie_Backend.Store.Store;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/*
    Chaean
    매장 상품 Entity
 */
@Entity
@Table(name = "store_product")
// 복합키
@IdClass(StoreProduct.StoreProductPK.class)
@Getter
@Setter
@ToString
public class StoreProduct {
    @Id
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "state")
    private Boolean state;

    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class StoreProductPK implements Serializable {
        private Long store;
        private Long product;
    }
}



