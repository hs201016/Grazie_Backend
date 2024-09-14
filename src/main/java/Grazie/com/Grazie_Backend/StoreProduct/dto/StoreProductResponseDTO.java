package Grazie.com.Grazie_Backend.StoreProduct.dto;

import Grazie.com.Grazie_Backend.Store.entity.Store;
import Grazie.com.Grazie_Backend.StoreProduct.entity.StoreProduct;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreProductResponseDTO {
    Store store;
    List<StoreProduct> storeProducts;
}
