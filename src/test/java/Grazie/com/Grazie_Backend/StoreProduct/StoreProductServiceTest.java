package Grazie.com.Grazie_Backend.StoreProduct;

import Grazie.com.Grazie_Backend.Product.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("StoreProduct 서비스 테스트")
class StoreProductServiceTest {

    @Autowired
    private StoreProductService storeProductService;

    @BeforeEach
    public void before() {
        System.out.println("테스트 시작");
    }

    @AfterEach
    public void after() {
        System.out.println("테스트 끝");
    }

    @Test
    public void getAllProductsByStoreIdTest() {
        List<Product> list =storeProductService.getAllProductsByStoreId(1L);

        for (Product product : list) {
            System.out.println(product.toString());
        }
    }

}