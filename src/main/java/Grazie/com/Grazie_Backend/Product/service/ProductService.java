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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        if (productDTO.getSmallPrice() < 0 ) {
            throw new InvalidFieldValueException("상품의 가격은 0보다 작을 수 없습니다: " + productDTO.getSmallPrice());
        }
        if (validationInformation(productDTO.getInformation())) {
            throw new InvalidFieldValueException("상품의 영양정보는 0보다 작을 수 없습니다: " + productDTO.getInformation().toString());
        }

        Product product = new Product();

        product.setName(productDTO.getName());
        product.setImage(uploadDir + "/" + productDTO.getImage());
        product.setSmallPrice(productDTO.getSmallPrice());
        product.setMediumPrice(productDTO.getMediumPrice());
        product.setLargePrice(productDTO.getLargePrice());
        product.setIceAble(productDTO.getIceAble());
        product.setHotAble(productDTO.getHotAble());;
        product.setExplanation(productDTO.getExplanation());
        product.setInformation(productDTO.getInformation());
        product.setAllergy(productDTO.getAllergy());
        product.setCategory(productDTO.getCategory());

        productRepository.save(product);

        return product;
    }

    // 모든 상품 조회
    public List<ProductDTO> getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : products) {
            ProductDTO dto = new ProductDTO();
            dto.setProductId(product.getProductId());
            dto.setName(product.getName());
            dto.setImage(product.getImage());
            dto.setSmallPrice(product.getSmallPrice());
            dto.setMediumPrice(product.getMediumPrice());
            dto.setLargePrice(product.getLargePrice());
            dto.setIceAble(product.getIceAble());
            dto.setHotAble(product.getHotAble());
            dto.setExplanation(product.getExplanation());
            dto.setInformation(product.getInformation());
            dto.setAllergy(product.getAllergy());
            dto.setCategory(product.getCategory());

            productDTOs.add(dto);
        }

        return productDTOs;
    }

    // 상품 상세보기
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        return ProductDTO.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .image(product.getImage())
                .smallPrice(product.getSmallPrice())
                .mediumPrice(product.getMediumPrice())
                .largePrice(product.getLargePrice())
                .iceAble(product.getIceAble())
                .hotAble(product.getHotAble())
                .explanation(product.getExplanation())
                .information(product.getInformation())
                .allergy(product.getAllergy())
                .category(product.getCategory())
                .build();
    }

    // product_id를 이용한 상품 수정
    public Product updateProductById(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        if (productDTO.getSmallPrice() < 0 ) {
            throw new InvalidFieldValueException("상품의 가격은 0보다 작을 수 없습니다: " + productDTO.getSmallPrice());
        }
        if (validationInformation(productDTO.getInformation())) {
            throw new InvalidFieldValueException("상품의 영양정보는 0보다 작을 수 없습니다: " + productDTO.getInformation().toString());
        }

        product.setName(productDTO.getName());
        product.setImage(uploadDir + "/" + productDTO.getImage());
        product.setSmallPrice(productDTO.getSmallPrice());
        product.setMediumPrice(productDTO.getMediumPrice());
        product.setLargePrice(productDTO.getLargePrice());
        product.setIceAble(productDTO.getIceAble());
        product.setHotAble(productDTO.getHotAble());
        product.setExplanation(productDTO.getExplanation());
        product.setInformation(productDTO.getInformation());
        product.setAllergy(productDTO.getAllergy());
        product.setCategory(productDTO.getCategory());

        productRepository.save(product);

        return product;
    }

    // 이미지 파일을 image/productImage에 저장
    public String uploadImage(MultipartFile file, String fileName) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("파일이 업로드되지 않았습니다");
        }

        // 환경변수에서 주입된 uploadDir이 유효한지 확인
        if (uploadDir == null || uploadDir.trim().isEmpty()) {
            throw new IllegalArgumentException("업로드 경로가 설정되지 않았습니다.");
        }

        try {
            // 업로드 경로가 존재하지 않으면 생성
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());

            return "이미지 업로드 성공: " + fileName;
        } catch (IOException e) {
            log.info("이미지 업로드 실패 = {}", fileName, e);
            throw new RuntimeException("이미지 업로드에 실패했습니다");
        }
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
}
