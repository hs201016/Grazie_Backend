package Grazie.com.Grazie_Backend.Product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/*
    Chaean
    상품 영양정보 class
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInformation {
    private Integer calories;
    private Integer saturatedFat;
    private Integer protein;
    private Integer sodium;
    private Integer sugar;
    private Integer caffeine;

    @Override
    public String toString() {
        return "ProductInformation{" +
                "calories=" + calories +
                ", saturatedFat=" + saturatedFat +
                ", protein=" + protein +
                ", sodium=" + sodium +
                ", sugar=" + sugar +
                ", caffeine=" + caffeine +
                '}';
    }
}
