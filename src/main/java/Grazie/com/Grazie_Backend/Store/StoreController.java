package Grazie.com.Grazie_Backend.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store")
public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/create")
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) {
        try {
            Store store = storeService.createStore(storeDTO);

            return ResponseEntity.ok(StoreDTO.builder()
                    .name(store.getName())
                    .tel_num(store.getTel_num())
                    .state(store.getState())
                    .location(store.getLocation())
                    .road_way(store.getRoad_way())
                    .parking(store.getParking())
                    .build());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StoreDTO());
        }
    }
}
