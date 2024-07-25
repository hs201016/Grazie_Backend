package Grazie.com.Grazie_Backend.Product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id; // 고유번호
    @Column(name = "name")
    private String name; // 상품 이름
    @Column(name = "image")
    private String image; // 상품 이미지 url
    @Column(name = "price")
    private Integer price; // 상품 가격
    @Column(name = "explanation")
    private String explanation; // 상품 설명
    @Column(name = "size")
    private String size; // 상품 사이즈
    @Column(name = "information")
    @JdbcTypeCode(SqlTypes.JSON)
    private ProductInformation information; // 상품 영양정보
    @Column(name = "temperature")
    private String temperature; // 음료온도

    public Product() {}
}
