package Grazie.com.Grazie_Backend.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAdditionalInfoRepository extends JpaRepository<User, Long> {
}
