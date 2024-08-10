package Grazie.com.Grazie_Backend.StoreProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
    Chaean
    매장 상품 Controller
 */

@RestController
@RequestMapping("/api/store_product")
public class StoreProductController {

    private final StoreProductService storeProductService;

    @Autowired
    public StoreProductController(StoreProductService storeProductService) {
        this.storeProductService = storeProductService;
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createStoreProduct(@RequestBody StoreProductDTO storeProductDTO) {
        try {
            storeProductService.createStoreProduct(storeProductDTO);
            return ResponseEntity.ok(true);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }
}
