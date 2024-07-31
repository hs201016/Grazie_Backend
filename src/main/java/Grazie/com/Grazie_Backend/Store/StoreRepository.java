package Grazie.com.Grazie_Backend.Store;

import org.springframework.data.jpa.repository.JpaRepository;

/*
    Chaean00
    매장 DB -> JPA
 */
public interface StoreRepository extends JpaRepository<Store, Long> {
    Integer countByName(String name);
}