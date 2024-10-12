package Grazie.com.Grazie_Backend.Product.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDistinctDTO {
    private String name;
    private String image;
    private int price;
    private String explanation; // 설명
    private ProductInformation information; // 영양 정보
    private String allergy; // 알러지 정보
    private String category; // 카테고리
}
