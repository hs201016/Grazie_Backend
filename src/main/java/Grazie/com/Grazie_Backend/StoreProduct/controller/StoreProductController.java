package Grazie.com.Grazie_Backend.StoreProduct.controller;

import Grazie.com.Grazie_Backend.StoreProduct.dto.StoreProductDTO;
import Grazie.com.Grazie_Backend.StoreProduct.dto.StoreProductResponseDTO;
import Grazie.com.Grazie_Backend.StoreProduct.entity.StoreProduct;
import Grazie.com.Grazie_Backend.StoreProduct.service.StoreProductService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "특정 매장에서 판매하는 상품을 추가합니다.", description = "특정 매장에서 판매하는 새로운 상품을 추가합니다.")
    public ResponseEntity<Boolean> createStoreProduct(@RequestBody StoreProductDTO storeProductDTO) {
        storeProductService.createStoreProduct(storeProductDTO);
        return ResponseEntity.ok(true);
    }

    @Transactional
    @DeleteMapping("/delete/{storeId}/{productId}")
    @Operation(summary = "특정 매장에서 판매하는 상품을 삭제합니다.", description = "storeId, productId를 통해 특정 매장에서 판매하는 상품을 삭제합니다.")
    public ResponseEntity<Boolean> deleteStoreProduct(@PathVariable(value = "storeId") Long storeId, @PathVariable(value = "productId") Long productId) {
        storeProductService.deleteStoreProduct(storeId, productId);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/get/{storeId}")
    @Operation(summary = "특정 매장에서 판매하는 모든 상품을 조회합니다.", description = "storeId를 통해 특정 매장에서 판매하는 모든 상품을 조회합니다.")
    public ResponseEntity<StoreProductResponseDTO> getProductByStoreId(@PathVariable(value = "storeId") Long storeId) {
        return ResponseEntity.ok(storeProductService.getProductByStoreId(storeId));
    }

    @PutMapping("/update/{storeId}/{productId}")
    @Operation(summary = "특정 매장에서 상품의 판매 가능 여부를 변경합니다.", description = "storeId, productId 통해 특정 매장에서 상품의 상태를 변경합니다.")
    public ResponseEntity<StoreProduct> updateState(@PathVariable Long storeId, @PathVariable Long productId) {
        StoreProduct updatedStoreProduct = storeProductService.updateStoreProductState(storeId, productId);
        return ResponseEntity.ok(updatedStoreProduct);
    }
}
