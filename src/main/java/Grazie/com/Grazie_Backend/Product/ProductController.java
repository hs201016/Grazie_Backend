package Grazie.com.Grazie_Backend.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 저장 API
    @PostMapping("/product/create")
    public ResponseEntity<Product> createProduct(ProductDTO productDTO) {
        try {
            Product product = productService.saveProduct(productDTO);
            return ResponseEntity.ok(product);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Product());
        }
    }
}
