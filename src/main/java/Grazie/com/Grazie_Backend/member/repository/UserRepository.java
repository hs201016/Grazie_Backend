package Grazie.com.Grazie_Backend.member.repository;

import Grazie.com.Grazie_Backend.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     Optional<User> findByUserId(String userid);

     Optional<User> findById(Long userId);
     Optional<User> findByEmail(String email);

     boolean existsByUserId(String userId);
     boolean existsByEmail(String email);

     boolean existsByPhone(String phone);


}
