package Grazie.com.Grazie_Backend.StoreProduct.controller;

import Grazie.com.Grazie_Backend.StoreProduct.dto.StoreProductDTO;
import Grazie.com.Grazie_Backend.StoreProduct.dto.StoreProductResponseDTO;
import Grazie.com.Grazie_Backend.StoreProduct.service.StoreProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/*
    Chaean
    매장 상품 Controller
 */

@RestController
@RequestMapping("/api/store_product")
@Slf4j
public class StoreProductController {

    private final StoreProductService storeProductService;

    @Autowired
    public StoreProductController(StoreProductService storeProductService) {
        this.storeProductService = storeProductService;
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createStoreProduct(@RequestBody StoreProductDTO storeProductDTO) {
//        try {
//            storeProductService.createStoreProduct(storeProductDTO);
//            return ResponseEntity.ok(true);
//        } catch (Exception exception) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
//        }
        storeProductService.createStoreProduct(storeProductDTO);
        return ResponseEntity.ok(true);
    }

    @Transactional
    @DeleteMapping("/delete/{storeId}/{productId}")
    public ResponseEntity<Boolean> deleteStoreProduct(@PathVariable(value = "storeId") Long storeId, @PathVariable(value = "productId") Long productId) {
        storeProductService.deleteStoreProduct(storeId, productId);
        return ResponseEntity.ok(true);
//        try {
//            storeProductService.deleteStoreProduct(storeId, productId);
//            return ResponseEntity.ok(true);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
//        } catch (Exception e) {
//            log.debug("storeId = " + storeId);
//            log.debug("productId = " + productId);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
//        }
    }

    @GetMapping("/get/{storeId}")
    public ResponseEntity<StoreProductResponseDTO> getProductByStoreId(@PathVariable(value = "storeId") Long storeId) {
//        try {
//            return ResponseEntity.ok(storeProductService.getProductByStoreId(storeId));
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StoreProductResponseDTO());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StoreProductResponseDTO());
//        }
        return ResponseEntity.ok(storeProductService.getProductByStoreId(storeId));
    }
}