package Grazie.com.Grazie_Backend.StoreProduct;

import Grazie.com.Grazie_Backend.Store.Store;
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
