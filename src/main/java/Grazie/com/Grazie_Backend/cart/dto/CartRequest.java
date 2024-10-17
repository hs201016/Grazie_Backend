package Grazie.com.Grazie_Backend.cart.dto;

import Grazie.com.Grazie_Backend.personaloptions.dto.PersonalOptionsRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {
    private Long productId;
    private int quantity;
    private PersonalOptionsRequest personalOptions;
}
