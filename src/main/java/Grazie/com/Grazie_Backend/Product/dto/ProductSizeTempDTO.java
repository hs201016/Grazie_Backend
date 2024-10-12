package Grazie.com.Grazie_Backend.Product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProductSizeTempDTO {
    private Long productId;
    private String size;
    private String temperature;
}
