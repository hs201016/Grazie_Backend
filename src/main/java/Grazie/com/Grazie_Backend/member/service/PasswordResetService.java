package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.member.entity.PasswordToken;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.PasswordTokenRepository;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordTokenRepository tokenRepository;

    public void generateTempPassword(String email) {

        User user  = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일 인증을 하는데 이런 이메일을 가진 유저가 없어요"));

        String tempPassword = UUID.randomUUID().toString().substring(0, 8);

        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        PasswordToken passwordToken = new PasswordToken(user, token, LocalDateTime.now().plusHours(1));
        tokenRepository.save(passwordToken);

        String subject = "임시 비밀번호를 발급해드렸습니다.";
        String text = "고객님의 임시 비밀번호는 " + tempPassword + "입니다. 정확하게 입력해주세요!\n" +
                "이걸 일단은 포스트맨에 넣어보세요! : " +
                "localhost:8080/users/changeTempPassword?=" + token;

        emailService.sendEmail(user.getEmail(), subject, text);
    }
}
