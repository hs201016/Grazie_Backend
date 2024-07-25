package Grazie.com.Grazie_Backend.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDTO {
    private String name;
    private String image;
    private Integer price;
    private String explanation;
    private String size;
    private ProductInformation information;
    private String temperature;
}
