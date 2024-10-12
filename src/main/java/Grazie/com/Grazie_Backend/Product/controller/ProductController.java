package Grazie.com.Grazie_Backend.Product.controller;

import Grazie.com.Grazie_Backend.Product.dto.ProductDTO;
import Grazie.com.Grazie_Backend.Product.dto.ProductDistinctDTO;
import Grazie.com.Grazie_Backend.Product.dto.ProductSizeTempDTO;
import Grazie.com.Grazie_Backend.Product.service.ProductService;
import Grazie.com.Grazie_Backend.Product.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/*
    Chaean
    상품 Controller
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 저장 API
    @PostMapping("/create")
    @Operation(summary = "상품을 생성합니다.", description = "새로운 상품을 생성합니다.")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.createProduct(productDTO);

        return ResponseEntity.ok(ProductDTO.builder()
                .product_id(product.getProductId())
                .name(product.getName())
                .image(product.getImage())
                .price(product.getPrice())
                .explanation(product.getExplanation())
                .size(product.getSize())
                .information(product.getInformation())
                .temperature(product.getTemperature())
                .build());
    }

    // 모든 상품 조회 API
    @GetMapping("/get/all")
    @Operation(summary = "모든 상품을 조회합니다.", description = "현재 생성되어있는 모든 상품을 조회합니다.")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping("/get/distinct/all")
    @Operation(summary = "이름 중복을 제거한 모든 상품을 조회합니다.", description = "현재 생성되어있는 상품을 이름 중복을 제거하여 조회합니다. 가격은 같은 이름의 상품 중 가장 저렴한 가격입니다")
    public ResponseEntity<List<ProductDistinctDTO>> getAllProductDistinct() {
        return ResponseEntity.ok(productService.getAllProductDistinct());
    }

    @GetMapping("/get/size-temp/{name}")
    public ResponseEntity<List<ProductSizeTempDTO>> getProductSizeTempByName(@PathVariable(value = "name") String name) {
        List<ProductSizeTempDTO> productSizeTempList = productService.getSizeTempByName(name);

        if (productSizeTempList.isEmpty()) {
            return ResponseEntity.noContent().build(); // 데이터가 없을 경우 204 No Content 반환
        }

        return ResponseEntity.ok(productSizeTempList); // 성공적으로 데이터를 반환할 경우 200 OK
    }
    // 특정 상품의 상세보기 API
    @GetMapping("/get/{id}")
    @Operation(summary = "특정 상품을 조회합니다.", description = "productId를 통해 특정 상품을 조회합니다.")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // product_id를 이용한 상품 수정 API
    @PutMapping("/update/{id}")
    @Operation(summary = "특정 상품을 수정합니다.", description = "productId를 통해 특정 상품을 업데이트합니다.")
    public ResponseEntity<ProductDTO> updateProductById(@PathVariable(value = "id") Long id, @RequestBody ProductDTO productDTO) {
        Product product = productService.updateProductById(id, productDTO);
        return ResponseEntity.ok(ProductDTO.builder()
                .product_id(product.getProductId())
                .name(product.getName())
                .image(product.getImage())
                .price(product.getPrice())
                .explanation(product.getExplanation())
                .size(product.getSize())
                .information(product.getInformation())
                .temperature(product.getTemperature())
                .build());
    }

    // product_id를 이용한 상품 삭제 API
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "특정 상품을 삭제합니다.", description = "productId를 통해 특정 상품을 삭제합니다.")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(productService.deleteProductById(id));
    }
}
