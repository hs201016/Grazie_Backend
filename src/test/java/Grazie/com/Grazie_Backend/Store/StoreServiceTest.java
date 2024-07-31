package Grazie.com.Grazie_Backend.Store;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
class StoreServiceTest {

    @Autowired
    private StoreService storeService;

    @BeforeEach
    public void before() {
        System.out.println("테스트 시작");
    }

    @AfterEach
    public void after() {
        System.out.println("테스트 끝");
    }

    @Test
    public void createStoreTest() {
        Store store1 = storeService.createStore(StoreDTO.builder()
                .name("GRAZIE 대진대학교 1호점")
                .tel_num(null)
                .state(true)
                .location("경기도 포천시 선단동 249")
                .road_way("대진대학교 학생회관 1층 야외매장")
                .parking(true)
                .build());

        Store store2 = storeService.createStore(StoreDTO.builder()
                .name("GRAZIE 대진대학교 2호점")
                .state(true)
                .location("경기도 포천시 선단동 249")
                .road_way("대진대학교 학생회관 CU옆")
                .parking(true)
                .build());


        Store store3 = storeService.createStore(StoreDTO.builder()
                .name("GRAZIE 대진대학교 3호점")
                .state(true)
                .location("경기도 포천시 선단동 산 11-1")
                .road_way("대진대학교 중앙 도서관 1층")
                .parking(true)
                .build());

        Store store4 = storeService.createStore(StoreDTO.builder()
                .name("GRAZIE 대진대학교 남자기숙사점")
                .tel_num("031-539-1444")
                .state(true)
                .location("경기도 포천시 호국로 1007")
                .road_way("대진대학교 남자기숙사 옆")
                .parking(false)
                .build());

        System.out.println(store1.toString());
        System.out.println(store2.toString());
        System.out.println(store3.toString());
        System.out.println(store4.toString());
    }

    @Test
    public void getAllStoreTest() {
        List<StoreDTO> list = storeService.getAllStore();

        for (StoreDTO dto : list) {
            System.out.println(dto.toString());
        }
    }

    @Test
    public void getStoreByIdTest() {
        StoreDTO storeDTO = storeService.getStoreById(1L);

        System.out.println(storeDTO.toString());
    }

    @Test
    public void updateStoreByIdTest() {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName("업데이트 테스트1");
        storeDTO.setTel_num("업데이트 테스트2");
        storeDTO.setState(false);
        storeDTO.setLocation("업데이트 테스트3");
        storeDTO.setRoad_way("업데이트 테스트4");
        storeDTO.setParking(false);

        Store store = storeService.updateStoreById(1L, storeDTO);

        System.out.println(store.toString());
    }

    @Test
    public void deleteStoreByIdTest() {
        System.out.println(storeService.deleteStoreById(1L));
    }
}
