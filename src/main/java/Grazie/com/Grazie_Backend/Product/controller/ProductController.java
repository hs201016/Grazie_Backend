package Grazie.com.Grazie_Backend.Product.controller;

import Grazie.com.Grazie_Backend.Product.dto.ProductDTO;
import Grazie.com.Grazie_Backend.Product.service.ProductService;
import Grazie.com.Grazie_Backend.Product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    // 특정 상품의 상세보기 API
    @GetMapping("/get/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // product_id를 이용한 상품 수정 API
    @PutMapping("/update/{id}")
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
    public ResponseEntity<Boolean> deleteProductById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(productService.deleteProductById(id));
    }
}
