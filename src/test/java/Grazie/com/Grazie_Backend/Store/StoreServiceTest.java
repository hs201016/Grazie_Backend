package Grazie.com.Grazie_Backend.Store;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}