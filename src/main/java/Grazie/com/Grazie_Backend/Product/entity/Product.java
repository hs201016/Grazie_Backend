package Grazie.com.Grazie_Backend.Product.entity;

import Grazie.com.Grazie_Backend.Product.dto.ProductInformation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@ToString
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

    @Column(name = "small_price", nullable = false)
    @NotNull
    private int smallPrice;

    @Column(name = "medium_price")
    private int mediumPrice;

    @Column(name = "large_price")
    private int largePrice;

    @Column(name = "ice_able")
    private Boolean iceAble;

    @Column(name = "hot_able")
    private Boolean hotAble;

    @Column(name = "explanation", nullable = false)
    @Size(max = 100)
    @NotNull
    private String explanation; // 상품 설명

    @Column(name = "information", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    @NotNull
    private ProductInformation information; // 상품 영양정보

    @Column(name = "allergy")
    @Size(max = 100)
    private String allergy; // 상품 알러지정보

    @Column(name = "category", nullable = false)
    @Size(max = 50)
    @NotNull
    private String category; // 상품 카테고리
}
