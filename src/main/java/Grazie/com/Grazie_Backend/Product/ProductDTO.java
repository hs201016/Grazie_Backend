package Grazie.com.Grazie_Backend.Product;

import lombok.*;
/*
    Chaean
    상품 DTO
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long product_id;
    private String name;
    private String image;
    private Integer price;
    private String explanation;
    private String size;
    private ProductInformation information;
    private String temperature;


    @Override
    public String toString() {
        return "ProductDTO{" +
                "product_id=" + product_id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", explanation='" + explanation + '\'' +
                ", size='" + size + '\'' +
                ", information=" + information.toString() +
                ", temperature='" + temperature + '\'' +
                '}';
    }
}
