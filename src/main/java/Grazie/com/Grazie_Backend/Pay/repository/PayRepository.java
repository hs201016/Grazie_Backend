package Grazie.com.Grazie_Backend.Pay.repository;

import Grazie.com.Grazie_Backend.Pay.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PayRepository extends JpaRepository<Pay, Long> {
//    List<Pay> findAllByBuyerName
//    @Query("SELECT p.impUid FROM Pay p WHERE p.buyerName = :buyerName")
//    List<Pay> findImpUidByBuyerName(@Param("buyerName") String buyerName);
    List<Pay> findAllByBuyerName(String buyerName);

    Optional<Pay> findByMerchantUid(String merchantUid);
}

