package Grazie.com.Grazie_Backend.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private Long id;
    private Long productId;
    private int quantity;
    private int productPrice;
    private int totalPrice;
}
