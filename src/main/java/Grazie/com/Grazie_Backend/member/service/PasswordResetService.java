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

        String subject = "비밀번호 찾기 안내입니다.";
        String text = "안녕하세요, 고객님!\n\n" +
                "요청하신 비밀번호 재발급 결과를 안내드립니다.\n\n" +
                "고객님의 임시 비밀번호는 \n" +
                "▶▶ **" + tempPassword + "** ◀◀\n\n" + "입니다" +
                "정확히 입력해 주시기 바랍니다.\n" +
                "이걸 일단은 포스트맨 주소창에 넣어보세요! : " +
                "34.64.110.210:8080/users/changeTempPassword?=" + token +
                "감사합니다.\n\n" +
                "Grazie Service Team 드림.";

        emailService.sendEmail(user.getEmail(), subject, text);
    }
}
