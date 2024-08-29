package Grazie.com.Grazie_Backend.Store;

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
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) {
        try {
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
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StoreDTO());
        }
    }

    // 모든 매장 조회 API
    @GetMapping("/get/all")
    public ResponseEntity<List<StoreDTO>> getAllStore() {
        try {
            return ResponseEntity.ok(storeService.getAllStore());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }

    // 매장 상세보기 API
    @GetMapping("/get/{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(storeService.getStoreById(id));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StoreDTO());
        }
    }

    // 오픈된 매장 조회 API
    @GetMapping("/get/open")
    public ResponseEntity<List<StoreDTO>> getOpenStore() {
        try {
            return ResponseEntity.ok(storeService.getOpenStore());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }

    // 주차 가능한 매장 조회 API
    @GetMapping("/get/parking")
    public ResponseEntity<List<StoreDTO>> getParkingStore() {
        try {
            return ResponseEntity.ok(storeService.getParkingStore());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }

    // store_id를 이용한 매장 업데이트 API
    @PutMapping("/update/{id}")
    public ResponseEntity<StoreDTO> updateStoreById(@PathVariable(value = "id") Long id, @RequestBody StoreDTO storeDTO) {
        try {
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
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StoreDTO());
        }
    }

    // store_id를 이용한 매장 삭제 API
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteStoreById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(storeService.deleteStoreById(id));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }
}