package Grazie.com.Grazie_Backend.Store.repository;

import Grazie.com.Grazie_Backend.Store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
    Chaean00
    매장 DB -> JPA
 */
public interface StoreRepository extends JpaRepository<Store, Long> {
    Integer countByName(String name);
    // 오픈된 매장을 가져오는 JPQL
    @Query("SELECT s FROM Store s WHERE s.state = true ")
    List<Store> findOpenStore();

    // 주차 가능한 매장을 가져오는 JPQL
    @Query("SELECT s FROM Store s WHERE s.parking = true ")
    List<Store> findParkingStore();
}