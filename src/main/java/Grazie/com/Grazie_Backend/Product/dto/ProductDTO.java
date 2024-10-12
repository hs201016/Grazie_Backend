package Grazie.com.Grazie_Backend.Product.dto;

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
@ToString
public class ProductDTO {
    private Long product_id;
    private String name;
    private String image;
    private Integer price;
    private String explanation;
    private String size;
    private ProductInformation information;
    private String temperature;
    private String allergy;
    private String category;
}
