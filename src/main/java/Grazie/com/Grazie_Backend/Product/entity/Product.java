package Grazie.com.Grazie_Backend.Product.entity;

import Grazie.com.Grazie_Backend.Product.dto.ProductInformation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
/*
    Chaean
    상품 Entity
 */
@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId; // 고유번호

    @Column(name = "name", nullable = false)
    @Size(max = 25)
    @NotNull
    private String name; // 상품 이름

    @Column(name = "image", nullable = false)
    @Size(max = 100)
    @NotNull
    private String image; // 상품 이미지 url

    @Column(name = "price", nullable = false)
    @Min(0)
    @NotNull
    private Integer price; // 상품 가격

    @Column(name = "explanation", nullable = false)
    @Size(max = 100)
    @NotNull
    private String explanation; // 상품 설명

    @Column(name = "size")
    @Size(max = 10)
    private String size; // 상품 사이즈

    @Column(name = "information", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    @NotNull
    private ProductInformation information; // 상품 영양정보

    @Column(name = "temperature")
    @Size(max = 5)
    private String temperature; // 음료온도

    @Column(name = "allergy")
    @Size(max = 100)
    private String allergy;

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + productId +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", explanation='" + explanation + '\'' +
                ", size='" + size + '\'' +
                ", information=" + information +
                ", temperature='" + temperature + '\'' +
                '}';
    }
}
