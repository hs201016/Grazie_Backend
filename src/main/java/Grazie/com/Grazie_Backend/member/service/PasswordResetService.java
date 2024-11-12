package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.global.exception.AppException;
import Grazie.com.Grazie_Backend.global.util.ErrorCode;
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

import static Grazie.com.Grazie_Backend.global.util.ErrorCode.*;

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
        PasswordToken passwordToken = validateToken(request.getToken());

        User user = passwordToken.getUser();
        if (!user.getPassword().equals(passwordEncoder.encode(request.getTempPassword()))) {
            throw new AppException(TEMP_PASSWORD_NOT_MATCH);
        }

        // 새로운 비번 설정
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // 사용 후 토큰 삭제!
        tokenRepository.delete(passwordToken);
    }


    private PasswordToken validateToken(String token) {
        PasswordToken passwordToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new AppException(INVALID_REFRESH_TOKEN));

        // 토큰 만료 확인
        if (passwordToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new AppException(REFRESH_TOKEN_EXPIRE);
        }

        return passwordToken;
    }

    public User validateUserIdAndEmail(String userId, String email) {
        // 1. 사용자 ID 존재 여부 확인
        if (!userRepository.existsByUserId(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND.withArgs(userId));
        }

        // 2. 이메일 존재 여부 확인
        if (!userRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.EMAIL_NOT_FOUND.withArgs(email));
        }

        // 3. ID와 이메일 일치 여부 확인
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND.withArgs(userId)));

        if (!user.getEmail().equals(email)) {
            throw new AppException(ID_EMAIL_NOT_MATCH);
        }
        return user;
    }
}
