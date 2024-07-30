package Grazie.com.Grazie_Backend.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store createStore(StoreDTO storeDTO) {

        Store store = new Store();

        store.setName(storeDTO.getName());
        store.setTel_num(storeDTO.getTel_num());
        store.setState(storeDTO.getState());
        store.setLocation(storeDTO.getLocation());
        store.setRoad_way(storeDTO.getRoad_way());
        store.setParking(storeDTO.getParking());

        storeRepository.save(store);

        return store;
    }
}
