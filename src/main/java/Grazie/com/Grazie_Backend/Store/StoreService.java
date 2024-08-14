package Grazie.com.Grazie_Backend.Store;

import Grazie.com.Grazie_Backend.StoreProduct.StoreProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
    Chaean
    매장 관리 기능
 */
@Service
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreProductRepository storeProductRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository, StoreProductRepository storeProductRepository) {
        this.storeRepository = storeRepository;
        this.storeProductRepository = storeProductRepository;
    }

    // 매장 생성
    public Store createStore(StoreDTO storeDTO) {
        if (validationStoreName(storeDTO.getName())) {
            throw new IllegalArgumentException("이미 존재하는 매장 이름입니다: " + storeDTO.getName());
        }

        Store store = new Store();

        store.setName(storeDTO.getName());
        store.setTel_num(storeDTO.getTel_num());
        store.setState(storeDTO.getState());
        store.setLocation(storeDTO.getLocation());
        store.setRoad_way(storeDTO.getRoad_way());
        store.setParking(storeDTO.getParking());

        return storeRepository.save(store);
    }

    // 모든 매장 검색
    public List<StoreDTO> getAllStore() {
        List<Store> stores = storeRepository.findAll();
        List<StoreDTO> storeDTOs = new ArrayList<>();

        for (Store store : stores) {
            StoreDTO dto = new StoreDTO();
            dto.setStore_id(store.getStore_id());
            dto.setName(store.getName());
            dto.setTel_num(store.getTel_num());
            dto.setState(store.getState());
            dto.setLocation(store.getLocation());
            dto.setRoad_way(store.getRoad_way());
            dto.setParking(store.getParking());

            storeDTOs.add(dto);
        }

        return storeDTOs;
    }

    // 매장 상세보기
    public StoreDTO getStoreById(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다."));

        return StoreDTO.builder()
                .store_id(store.getStore_id())
                .name(store.getName())
                .tel_num(store.getTel_num())
                .state(store.getState())
                .location(store.getLocation())
                .road_way(store.getRoad_way())
                .parking(store.getParking())
                .build();
    }

    // 오픈 되어 있는 매장 검색
    public List<StoreDTO> getOpenStore() {
        List<Store> stores = storeRepository.findOpenStore();
        List<StoreDTO> storeDTOs = new ArrayList<>();

        for (Store store : stores) {
            StoreDTO dto = new StoreDTO();

            dto.setStore_id(store.getStore_id());
            dto.setName(store.getName());
            dto.setState(store.getState());
            dto.setTel_num(store.getTel_num());
            dto.setLocation(store.getLocation());
            dto.setRoad_way(store.getRoad_way());
            dto.setParking(store.getParking());

            storeDTOs.add(dto);
        }

        return storeDTOs;
    }

    // 주차 가능한 매장 검색
    public List<StoreDTO> getParkingStore() {
        List<Store> stores = storeRepository.findParkingStore();
        List<StoreDTO> storeDTOs = new ArrayList<>();

        for (Store store : stores) {
            StoreDTO dto = new StoreDTO();

            dto.setStore_id(store.getStore_id());
            dto.setName(store.getName());
            dto.setState(store.getState());
            dto.setTel_num(store.getTel_num());
            dto.setLocation(store.getLocation());
            dto.setRoad_way(store.getRoad_way());
            dto.setParking(store.getParking());

            storeDTOs.add(dto);
            log.debug(dto.toString());
        }


        return storeDTOs;
    }

    // 매장 수정
    public Store updateStoreById(Long id, StoreDTO storeDTO) {
        if (validationStoreName(storeDTO.getName())) {
            throw new IllegalArgumentException("이미 존재하는 매장 이름입니다: " + storeDTO.getName());
        }

        Store store = storeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다."));

        store.setStore_id(id);
        store.setName(storeDTO.getName());
        store.setTel_num(storeDTO.getTel_num());
        store.setState(storeDTO.getState());
        store.setLocation(storeDTO.getLocation());
        store.setRoad_way(storeDTO.getRoad_way());
        store.setParking(storeDTO.getParking());

        return storeRepository.save(store);
    }

    // 매장 삭제
    public Boolean deleteStoreById(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("매장을 찾을 수 없습니다."));

        storeProductRepository.deleteByStore(store);
        storeRepository.delete(store);
        return true;
    }

    // 동일한 매장의 이름이 존재한다면 True
    private Boolean validationStoreName(String name) {
        return storeRepository.countByName(name) > 0;
    }
}