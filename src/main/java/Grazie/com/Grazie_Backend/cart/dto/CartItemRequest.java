package Grazie.com.Grazie_Backend.cart.dto;

import Grazie.com.Grazie_Backend.personaloptions.entity.PersonalOptions;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItemRequest {
    private Long productId;
    private String size;
    private String temperature;
    private int quantity;
    private PersonalOptions personalOptions;
}