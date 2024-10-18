package Grazie.com.Grazie_Backend.cart.dto;

import Grazie.com.Grazie_Backend.personaloptions.entity.PersonalOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private String productName;
    private String size;
    private String temperature;
    private int price;
    private int quantity;
    private PersonalOptions personalOptions;
}
