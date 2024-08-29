package Grazie.com.Grazie_Backend.StoreProduct;

import lombok.*;
/*
    Chaean
    매장 상품 DTO
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreProductDTO {
    private Long storeId;
    private Long productId;
    private Boolean state;
}
