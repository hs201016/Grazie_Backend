package Grazie.com.Grazie_Backend.member.repository;

import Grazie.com.Grazie_Backend.member.entity.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {

    Optional<PasswordToken> findByToken(String token);
}
