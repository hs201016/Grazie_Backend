package Grazie.com.Grazie_Backend.Product;

import Grazie.com.Grazie_Backend.Product.dto.ProductDTO;
import Grazie.com.Grazie_Backend.Product.dto.ProductInformation;
import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.Product.service.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.List;

@SpringBootTest
//@Transactional
@DisplayName("상품 관련 서비스 테스트")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @BeforeEach
    public void before() {
        System.out.println("Test Before Test");

    }

    @AfterEach
    public void after(){
        System.out.println("Test After");
    }

    @Test
    @DisplayName("모든 상품 조회 테스트")
    public void getAllProductTest() {
        List<ProductDTO> products = productService.getAllProduct();
        for (ProductDTO dto : products) {
            System.out.println(dto.toString());

            System.out.println();
        }
    }

    @Test
    @DisplayName("상품 생성 테스트")
    public void createProductTest() {
        ProductDTO dto = new ProductDTO();

        dto.setName("test");
        dto.setImage("test");
        dto.setSmallPrice(1111);
        dto.setMediumPrice(2222);
        dto.setLargePrice(3333);
        dto.setIceAble(true);
        dto.setHotAble(true);
        dto.setExplanation("test");
        dto.setInformation(new ProductInformation(1,1,1,1,1,1));
        dto.setAllergy("test");
        dto.setCategory("test");

        Product product = productService.createProduct(dto);

        System.out.println(product);
    }
    @Test
    @DisplayName("상품 상세보기 테스트_실패")
    public void 상품상세보기테스트_실패() {
        ProductDTO dto = productService.getProductById(123L);

        System.out.println(dto.toString());
    }
    @Test
    public void 상품상세보기테스트_성공() {
        ProductDTO dto = productService.getProductById(5L);

        System.out.println(dto.toString());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    public void updateProductTest() {
        ProductDTO dto = new ProductDTO();

        dto.setName("testU");
        dto.setImage("testU");
        dto.setSmallPrice(11);
        dto.setMediumPrice(22);
        dto.setLargePrice(33);
        dto.setIceAble(false);
        dto.setHotAble(false);
        dto.setExplanation("testU");
        dto.setInformation(new ProductInformation(2,2,2,2,2,2));
        dto.setAllergy("testU");
        dto.setCategory("testU");

        Product product = productService.updateProductById(61L, dto);

        System.out.println(product);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    public void deleteProductByIdTest() {
        boolean flag = productService.deleteProductById(61L);

        System.out.println(flag);
    }
}