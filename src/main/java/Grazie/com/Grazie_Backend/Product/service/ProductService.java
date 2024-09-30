package Grazie.com.Grazie_Backend.Product.service;

import Grazie.com.Grazie_Backend.Product.dto.ProductDTO;
import Grazie.com.Grazie_Backend.Product.dto.ProductInformation;
import Grazie.com.Grazie_Backend.Product.entity.Product;
import Grazie.com.Grazie_Backend.Product.exception.InvalidFieldValueException;
import Grazie.com.Grazie_Backend.Product.exception.ProductNotFoundException;
import Grazie.com.Grazie_Backend.Product.repository.ProductRepository;
import Grazie.com.Grazie_Backend.StoreProduct.repository.StoreProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/*
    Chaean00
    Product(상품) 관련 Service
 */
@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreProductRepository storeProductRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    public ProductService(ProductRepository productRepository, StoreProductRepository storeProductRepository) {
        this.productRepository = productRepository;
        this.storeProductRepository = storeProductRepository;
    }



    // 상품 생성
    public Product createProduct(ProductDTO productDTO) {
        if (validationProduct(productDTO.getName())) {
            throw new InvalidFieldValueException("이미 존재하는 상품 이름입니다: " + productDTO.getName());
        }
        if (productDTO.getPrice() < 0 ) {
            throw new InvalidFieldValueException("상품의 가격은 0보다 작을 수 없습니다: " + productDTO.getPrice());
        }
        if (validationInformation(productDTO.getInformation())) {
            throw new InvalidFieldValueException("상품의 영양정보는 0보다 작을 수 없습니다: " + productDTO.getInformation().toString());
        }
        if (validationTemperature(productDTO.getTemperature())) {
            throw new InvalidFieldValueException("상품의 온도가 정해진 문자열이 아닙니다: " + productDTO.getTemperature());
        }
        if (validationSize(productDTO.getSize())) {
            throw new InvalidFieldValueException("상품의 사이즈가 정해진 문자열이 아닙니다: " + productDTO.getSize());
        }

        Product product = new Product();

        product.setName(productDTO.getName());
        // 이미지 파일 저장
        product.setImage(uploadDir + "/" + productDTO.getImage() +".jpg");
        product.setPrice(productDTO.getPrice());
        product.setExplanation(productDTO.getExplanation());
        product.setSize(productDTO.getSize());
        product.setInformation(productDTO.getInformation());
        product.setTemperature(productDTO.getTemperature());


        productRepository.save(product);

        return product;
    }

    // 모든 상품 가져오기
    public List<ProductDTO> getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : products) {
            ProductDTO dto = new ProductDTO();
            dto.setProduct_id(product.getProductId());
            dto.setName(product.getName());
            dto.setImage(product.getImage());
            dto.setPrice(product.getPrice());
            dto.setExplanation(product.getExplanation());
            dto.setSize(product.getSize());
            dto.setInformation(product.getInformation());
            dto.setTemperature(product.getTemperature());

            productDTOs.add(dto);
        }

        return productDTOs;
    }

    // 상품 상세보기
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        return ProductDTO.builder()
                .product_id(product.getProductId())
                .name(product.getName())
                .image(product.getImage())
                .price(product.getPrice())
                .explanation(product.getExplanation())
                .size(product.getSize())
                .information(product.getInformation())
                .temperature(product.getTemperature())
                .build();
    }

    // product_id를 이용한 상품 수정
    public Product updateProductById(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        if (productDTO.getPrice() < 0 ) {
            throw new InvalidFieldValueException("상품의 가격은 0보다 작을 수 없습니다: " + productDTO.getPrice());
        }
        if (validationInformation(productDTO.getInformation())) {
            throw new InvalidFieldValueException("상품의 영양정보는 0보다 작을 수 없습니다: " + productDTO.getInformation().toString());
        }
        if (validationTemperature(productDTO.getTemperature())) {
            throw new InvalidFieldValueException("상품의 온도가 정해진 문자열이 아닙니다: " + productDTO.getTemperature());
        }
        if (validationSize(productDTO.getSize())) {
            throw new InvalidFieldValueException("상품의 사이즈가 정해진 문자열이 아닙니다: " + productDTO.getSize());
        }

        product.setName(productDTO.getName());
        product.setImage(uploadDir + "/" + productDTO.getImage() +".jpg");
        product.setPrice(productDTO.getPrice());
        product.setExplanation(productDTO.getExplanation());
        product.setSize(productDTO.getSize());
        product.setInformation(productDTO.getInformation());
        product.setTemperature(productDTO.getTemperature());

        productRepository.save(product);

        return product;
    }

    /*
     product_id를 이용한 상품 삭제
     상품 삭제에 성공했다면 True
     */
    public boolean deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        storeProductRepository.deleteByProduct(product);
        productRepository.delete(product);
        return true;
    }

    // 상품 이름으로 중복 확인
    // 동일한 이름의 제품이 존재한다면 True
    private Boolean validationProduct(String name) {
        return productRepository.countByName(name) > 0;
    }

    // 상품의 영양정보 확인
    // 영양정보가 잘못되었다면 True
    private boolean validationInformation(ProductInformation info) {
        return info.getCalories() < 0 || info.getSaturatedFat() < 0 || info.getProtein() < 0
                || info.getSodium() < 0 || info.getSugar() < 0 || info.getCaffeine() < 0;
    }

    // 상품의 온도가 정해진 문자열이 아닌 경우 True
    private boolean validationTemperature(String tempe) {
        return !tempe.equals("both") && !tempe.equals("hot") && !tempe.equals("ice") && !tempe.equals("non");
    }

    // 상품의 온도가 정해진 문자열이 아닌 경우 True
    private boolean validationSize(String size) {
        return !size.equals("tall") && !size.equals("grande") && !size.equals("venti") && !size.equals("non");
    }
}
