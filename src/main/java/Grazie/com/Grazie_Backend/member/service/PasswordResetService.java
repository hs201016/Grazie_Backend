package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.member.dto.ResetPasswordRequest;
import Grazie.com.Grazie_Backend.member.dto.TempPasswordRequest;
import Grazie.com.Grazie_Backend.member.entity.PasswordToken;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.PasswordTokenRepository;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordTokenRepository tokenRepository;

    public void generateTempPassword(TempPasswordRequest request) {

        User user = validateUserIdAndEmail(request.getUserId(), request.getEmail());

        // 임시 비밀번호 생성
        String tempPassword = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        PasswordToken passwordToken = new PasswordToken(user, token, LocalDateTime.now().plusHours(1));
        tokenRepository.save(passwordToken);

        String subject = "비밀번호 재설정 요청 안내";
        String text = "안녕하세요, 고객님!\n\n" +
                "비밀번호 재설정 요청이 접수되었습니다. 아래 링크를 클릭하여 비밀번호를 재설정해주세요.\n\n" +
                "비밀번호 재설정 링크: http://34.64.110.210:8080/reset-password?token=" + token + "\n\n" +
                "이 링크는 1시간 후에 만료됩니다.\n\n" +
                "임시 비밀번호는 다음과 같습니다: " + tempPassword + "\n\n" +
                "Grazie Service Team 드림.";

        emailService.sendEmail(user.getEmail(), subject, text);
    }


    public void resetPasswordUsingTempPassword(ResetPasswordRequest request) {
        // 검증 로직을 호출
        PasswordToken passwordToken = validateTokenAndTempPassword(request.getToken(), request.getTempPassword());

        User user = passwordToken.getUser();
        if (!user.getPassword().equals(passwordEncoder.encode(request.getTempPassword()))) {
            throw new EntityNotFoundException("임시 비밀번호가 올바르지 않습니다.");
        }

        // 새로운 비번 설정
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);


        // 사용 후 토큰 삭제!
        tokenRepository.delete(passwordToken);
    }


    private PasswordToken validateTokenAndTempPassword(String token, String tempPassword) {
        PasswordToken passwordToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("유효하지 않은 토큰입니다."));

        // 토큰 만료 확인
        if (passwordToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new EntityNotFoundException("토큰이 만료되었습니다.");
        }

        // 사용자가 입력한 임시 비밀번호 검증

        return passwordToken;
    }

    public User validateUserIdAndEmail(String userId, String email) {
        // 1. 사용자 ID 체크
        Optional<User> userById = userRepository.findByUserId(userId);
        if (userById.isEmpty()) {
            throw new EntityNotFoundException("해당 사용자 ID가 존재하지 않습니다.");
        }

        // 2. 이메일 체크
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isEmpty()) {
            throw new EntityNotFoundException("해당 이메일이 존재하지 않습니다.");
        }

        // 3. ID와 이메일 일치 여부 확인
        User user = userById.get();
        if (!user.getEmail().equals(email)) {
            throw new EntityNotFoundException("사용자 ID와 이메일이 일치하지 않습니다.");
        }

        return user;
    }

}
