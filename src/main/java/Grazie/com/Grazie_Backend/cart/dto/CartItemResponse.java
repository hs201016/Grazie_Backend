package Grazie.com.Grazie_Backend.cart.dto;

import Grazie.com.Grazie_Backend.personaloptions.dto.PersonalOptionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Long productId;
    private Long CartId;
    private String productName;
    private String size;
    private String temperature;
    private int price;
    private int quantity;
    private PersonalOptionResponse personalOptions;
    private String image;
}
