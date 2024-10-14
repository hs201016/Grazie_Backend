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
    private Long productId;
    private String name;
    private String image;
    private Integer smallPrice;
    private Integer mediumPrice;
    private Integer largePrice;
    private Boolean iceAble;
    private Boolean hotAble;
    private String explanation;
    private ProductInformation information;
    private String allergy;
    private String category;
}
