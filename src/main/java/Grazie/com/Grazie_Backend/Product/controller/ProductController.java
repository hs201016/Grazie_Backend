package Grazie.com.Grazie_Backend.Product.controller;

import Grazie.com.Grazie_Backend.Product.dto.ProductDTO;
import Grazie.com.Grazie_Backend.Product.dto.UploadImageDTO;
import Grazie.com.Grazie_Backend.Product.service.ProductService;
import Grazie.com.Grazie_Backend.Product.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.createProduct(productDTO);

        return ResponseEntity.ok(product);
    }

    // 모든 상품 조회 API
    @GetMapping("/get/all")
    @Operation(summary = "모든 상품을 조회합니다.", description = "현재 생성되어있는 모든 상품을 조회합니다.")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
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
    public ResponseEntity<Product> updateProductById(@PathVariable(value = "id") Long id, @RequestBody ProductDTO productDTO) {
        Product product = productService.updateProductById(id, productDTO);
        return ResponseEntity.ok(product);
    }

    // product_id를 이용한 상품 삭제 API
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "특정 상품을 삭제합니다.", description = "productId를 통해 특정 상품을 삭제합니다.")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(productService.deleteProductById(id));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadProductImage(@RequestParam MultipartFile file,
                                                     @RequestParam("fileName") String fileName) throws IOException {
        // 파일 이름이 비어 있는 경우 기본 파일 이름 사용
        if (fileName == null || fileName.isEmpty()) {
            fileName = file.getOriginalFilename();
        }

        productService.uploadImage(file, fileName);
        return ResponseEntity.ok("이미지 업로드에 성공했습니다");
    }
}
