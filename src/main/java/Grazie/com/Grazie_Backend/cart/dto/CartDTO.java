package Grazie.com.Grazie_Backend.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
    private Long userId;
    private Long productId;
    private int quantity;

    public CartDTO() {
    }

    public CartDTO(Long userId, Long productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
