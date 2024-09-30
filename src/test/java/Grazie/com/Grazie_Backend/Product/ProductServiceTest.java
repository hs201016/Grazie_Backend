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

import java.util.List;

@SpringBootTest
@Transactional
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
            System.out.print(dto.getName() + " " + dto.getImage() + " " + dto.getPrice() + " " + dto.getExplanation()
            + " " + dto.getSize() + " " + " " + dto.getTemperature());

            System.out.print(" 영양정보 = " + dto.getInformation().getCalories() + " " + dto.getInformation().getSaturatedFat() + " "
            + dto.getInformation().getProtein() + " " + dto.getInformation().getSodium() + " " + dto.getInformation().getSugar() + " "
            + dto.getInformation().getCaffeine());

            System.out.println();
        }
    }

    @Test
    @DisplayName("상품 생성 테스트")
    public void createProductTest() {

        ProductInformation productInformation = new ProductInformation();
        productInformation.setCalories(10);
        productInformation.setSaturatedFat(0);
        productInformation.setProtein(0);
        productInformation.setSodium(5);
        productInformation.setSugar(0);
        productInformation.setCaffeine(150);

        ProductDTO americano = new ProductDTO();
        americano.setName("아메리카노1");
        americano.setImage("Americano");
        americano.setPrice(0);
        americano.setExplanation("진한 에스프레소와 뜨거운 물을 섞어 스타벅스의 깔끔하고 강렬한 에스프레소를 가장 부드럽게 잘 느낄 수 있는 커피");
        americano.setSize("tall");
        americano.setInformation(productInformation);
        americano.setTemperature("both");

        ProductInformation productInformation1 = new ProductInformation();
        productInformation1.setCalories(180);
        productInformation1.setSaturatedFat(5);
        productInformation1.setProtein(10);
        productInformation1.setSodium(115);
        productInformation1.setSugar(13);
        productInformation1.setCaffeine(75);

        ProductDTO caffe_latte = new ProductDTO();
        caffe_latte.setName("카페 라떼");
        caffe_latte.setImage("Caffe Latte");
        caffe_latte.setPrice(5000);
        caffe_latte.setExplanation("풍부하고 진한 에스프레소가 신선한 스팀 밀크를 만나 부드러워진 커피 위에 우유 거품을 살짝 얹은 대표적인 커피 라떼");
        caffe_latte.setSize("tall");
        caffe_latte.setInformation(productInformation1);
        caffe_latte.setTemperature("both");

        ProductInformation productInformation2 = new ProductInformation();
        productInformation2.setCalories(290);
        productInformation2.setSaturatedFat(9);
        productInformation2.setProtein(10);
        productInformation2.setSodium(105);
        productInformation2.setSugar(25);
        productInformation2.setCaffeine(95);

        ProductDTO caffe_mocha = new ProductDTO();
        caffe_mocha.setName("카페 모카");
        caffe_mocha.setImage("Caffe Mocha");
        caffe_mocha.setPrice(5000);
        caffe_mocha.setExplanation("진한 초콜릿 모카 시럽과 풍부한 에스프레소를 스팀 밀크와 섞어 휘핑크림으로 마무리한 음료로 진한 에스프레소와 초콜릿 맛이 어우러진 커피");
        caffe_mocha.setSize("tall");
        caffe_mocha.setInformation(productInformation2);
        caffe_mocha.setTemperature("both");

        ProductInformation productInformation3 = new ProductInformation();
        productInformation3.setCalories(5);
        productInformation3.setSaturatedFat(0);
        productInformation3.setProtein(0);
        productInformation3.setSodium(0);
        productInformation3.setSugar(0);
        productInformation3.setCaffeine(75);

        ProductDTO espresso = new ProductDTO();
        espresso.setName("에스프레소");
        espresso.setImage("Espresso");
        espresso.setPrice(3500);
        espresso.setExplanation("스타벅스 에스프레소는 향기로운 크레마 층과 바디 층, 하트 층으로 이루어져 있으며, 입안 가득히 커피와 달콤한 카라멜 향이 느껴지는 커피 음료");
        espresso.setSize("solo");
        espresso.setInformation(productInformation3);
        espresso.setTemperature("hot");

        ProductInformation productInformation4 = new ProductInformation();
        productInformation4.setCalories(110);
        productInformation4.setSaturatedFat(3);
        productInformation4.setProtein(6);
        productInformation4.setSodium(70);
        productInformation4.setSugar(8);
        productInformation4.setCaffeine(75);

        ProductDTO cappuccino = new ProductDTO();
        cappuccino.setName("카푸치노");
        cappuccino.setImage("Cappuccino");
        cappuccino.setPrice(5500);
        cappuccino.setExplanation("풍부하고 진한 에스프레소에 따뜻한 우유와 벨벳 같은 우유 거품이 1:1 비율로 어우러져 마무리된 커피 음료");
        cappuccino.setSize("tall");
        cappuccino.setInformation(productInformation4);
        cappuccino.setTemperature("both");

        productService.createProduct(americano);
        productService.createProduct(caffe_latte);
        productService.createProduct(caffe_mocha);
        productService.createProduct(espresso);
        productService.createProduct(cappuccino);

    }

    @Test
    @DisplayName("상품 상세보기 테스트")
    public void getProductByIdTest() {
        ProductDTO dto = productService.getProductById(21L);

        System.out.println(dto.toString());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    public void updateProductTest() {
        ProductInformation productInformation = new ProductInformation();
        productInformation.setCalories(0);
        productInformation.setSaturatedFat(9);
        productInformation.setProtein(9);
        productInformation.setSodium(9);
        productInformation.setSugar(9);
        productInformation.setCaffeine(9);

        ProductDTO updateAmericano = new ProductDTO();
        updateAmericano.setName("수정 된아메리카노");
        updateAmericano.setImage("Americano");
        updateAmericano.setPrice(1231237128);
        updateAmericano.setExplanation("수정 아메리카노 테스트");
        updateAmericano.setSize("tall");
        updateAmericano.setInformation(productInformation);
        updateAmericano.setTemperature("both");

        Product product = productService.updateProductById(21L, updateAmericano);

        System.out.println(product.toString());
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    public void deleteProductByIdTest() {
        boolean flag = productService.deleteProductById(190L);

        System.out.println(flag);
    }
}