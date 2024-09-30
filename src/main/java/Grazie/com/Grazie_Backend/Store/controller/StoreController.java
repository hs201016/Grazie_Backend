package Grazie.com.Grazie_Backend.Store.controller;

import Grazie.com.Grazie_Backend.Store.dto.StoreDTO;
import Grazie.com.Grazie_Backend.Store.service.StoreService;
import Grazie.com.Grazie_Backend.Store.entity.Store;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
    Chaean00
    매장 관련 API
 */
@RestController
@RequestMapping("/api/store")
public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    // 매장 생성 API
    @PostMapping("/create")
    @Operation(summary = "매장을 생성합니다.", description = "특정 매장을 생성합니다.")
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) {
        Store store = storeService.createStore(storeDTO);

        return ResponseEntity.ok(StoreDTO.builder()
                .store_id(store.getStore_id())
                .name(store.getName())
                .tel_num(store.getTel_num())
                .state(store.getState())
                .location(store.getLocation())
                .road_way(store.getRoad_way())
                .parking(store.getParking())
                .operatingHours(store.getOperatingHours())
                .build());
    }

    // 모든 매장 조회 API
    @GetMapping("/get/all")
    @Operation(summary = "모든 매장을 조회합니다.", description = "현재 생성되어있는 모든 매장을 조회합니다.")
    public ResponseEntity<List<StoreDTO>> getAllStore() {
        return ResponseEntity.ok(storeService.getAllStore());
    }

    // 매장 상세보기 API
    @GetMapping("/get/{id}")
    @Operation(summary = "특정 매장을 조회합니다.", description = "storeId를 통해 특정 매창을 조회합니다.")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    // 오픈된 매장 조회 API
    @GetMapping("/get/open")
    @Operation(summary = "오픈한 매장을 조회합니다.", description = "오픈상태인 매장을 조회합니다.")
    public ResponseEntity<List<StoreDTO>> getOpenStore() {
        return ResponseEntity.ok(storeService.getOpenStore());
    }

    // 주차 가능한 매장 조회 API
    @GetMapping("/get/parking")
    @Operation(summary = "주차 가능한 매장을 조회합니다.", description = "주차 가능한 매장을 조회합니다.")
    public ResponseEntity<List<StoreDTO>> getParkingStore() {
        return ResponseEntity.ok(storeService.getParkingStore());
    }

    // store_id를 이용한 매장 업데이트 API
    @PutMapping("/update/{id}")
    @Operation(summary = "특정 매장을 업데이트합니다.", description = "storeId를 통해 특정 매장을 업데이트합니다.")
    public ResponseEntity<StoreDTO> updateStoreById(@PathVariable(value = "id") Long id, @RequestBody StoreDTO storeDTO) {
        Store store = storeService.updateStoreById(id, storeDTO);
        return ResponseEntity.ok(StoreDTO.builder()
                .store_id(store.getStore_id())
                .name(store.getName())
                .tel_num(store.getTel_num())
                .state(store.getState())
                .location(store.getLocation())
                .road_way(store.getRoad_way())
                .parking(store.getParking())
                .operatingHours(store.getOperatingHours())
                .build());
    }

    // store_id를 이용한 매장 삭제 API
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "매장을 삭제합니다.", description = "storeId를 통해 특정 매장을 삭제합니다.")
    public ResponseEntity<Boolean> deleteStoreById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(storeService.deleteStoreById(id));
    }
}