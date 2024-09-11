package Grazie.com.Grazie_Backend.cart.dto;

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
    private String temperature;
    private String size;
}

