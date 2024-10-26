package Grazie.com.Grazie_Backend.Product.service;


import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.Product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 사이즈 별로 금액을 측정하는 메소드 희수가 만듬.
 * 마찬가지로 예외는 한꺼번에 처리하겠습니다.
 */
@Service
@RequiredArgsConstructor
public class ProductCalcService {

    private final ProductRepository productRepository;

    public int getSizePrice(String size, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾지 못했습니다."));
        switch (size) {
            case "small":
                return product.getSmallPrice();
            case "medium":
                return product.getMediumPrice();
            case "large":
                return product.getLargePrice();
            default:
                throw new IllegalArgumentException("유효하지 않은 사이즈입니다.: " + size);
        }
    }

    private int getTemperaturePrice(String temperature, Long productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾지 못했습니다."));
        if(temperature.equals("ice"))
            return 300;
        else if ("hot".equals(temperature)) {
            return 0;
        } else {
            throw new IllegalArgumentException("유효하지 않은 온도입니다.: " + temperature);
        }
    }

    public int calculateTotalPrice(String size, String temperature, Long productId) {
        int sizePrice = getSizePrice(size, productId);
        int temperaturePrice = getTemperaturePrice(temperature, productId);
        return sizePrice + temperaturePrice;
    }
}

