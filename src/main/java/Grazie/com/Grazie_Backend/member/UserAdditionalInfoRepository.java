package Grazie.com.Grazie_Backend.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAdditionalInfoRepository extends JpaRepository<UserAdditionalInfo, Long> {

    Optional<UserAdditionalInfo> findByUserId(Long userId);
}
