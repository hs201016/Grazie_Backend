package Grazie.com.Grazie_Backend.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDeleteDTO {
    private Long cartId; // cart? user?
    private Long productId;
    private int quantity;

    @Override
    public String toString() {
        return "CartDeleteDTO{" +
                "cartId=" + cartId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
