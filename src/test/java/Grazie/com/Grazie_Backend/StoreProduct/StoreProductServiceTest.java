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
    @DisplayName("StoreId를 이용해 해당 매장에 모든 상품 조회")
    public void getAllProductsByStoreIdTest() {
        List<Product> list = storeProductService.getAllProductsByStoreId(2L);

        for (Product product : list) {
            System.out.println(product.toString());
        }
    }

    @Test
    @DisplayName("매장에서 판매하는 상품 등록")
    public void createStoreProductTest() {
        StoreProductDTO storeProductDTO = new StoreProductDTO();
        storeProductDTO.setStoreId(1L);
        storeProductDTO.setProductId(24L);
        storeProductDTO.setState(true);

        StoreProduct storeProduct = storeProductService.createStoreProduct(storeProductDTO);
        System.out.println(storeProduct.toString());
    }

    @Test
    @DisplayName("매장 판매 상품 삭제")
    public void deleteStoreProductTest() {
        if (storeProductService.deleteStoreProduct(1L, 21L)) {
            System.out.println("성공");
        } else {
            System.out.println("실패");
        }
    }

    @Test
    @DisplayName("특정 매장에서 판매하는 상품 조회")
    public void getProductByStoreIdTest() {
        List<StoreProduct> storeProducts = storeProductService.getProductByStoreId(1L);

        for (StoreProduct sp : storeProducts) {
            System.out.println(sp.toString());
        }
    }
}