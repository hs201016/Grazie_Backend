package Grazie.com.Grazie_Backend.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class CartItemResponseDTO {
    private Long productId;
    private int quantity;
    private int price;

    @Override
    public String toString() {
        return "CartItemResponseDTO{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}

