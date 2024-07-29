package Grazie.com.Grazie_Backend.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            Product product = productService.createProduct(productDTO);
            return ResponseEntity.ok(product);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Product());
        }
    }

    // 모든 상품 조회 API
    @GetMapping("/get/all")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        try {
            return ResponseEntity.ok(productService.getAllProduct());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }

    // 특정 상품의 상세보기 API
    @GetMapping("/get/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProductDTO());
        }
    }

    // product_id를 이용한 상품 수정 API
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable(value = "id") Long id, @RequestBody ProductDTO productDTO) {
        try {
            return ResponseEntity.ok(productService.updateProductById(id, productDTO));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Product());
        }
    }

    // product_id를 이용한 상품 삭제 API
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(productService.deleteProductById(id));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }
}
