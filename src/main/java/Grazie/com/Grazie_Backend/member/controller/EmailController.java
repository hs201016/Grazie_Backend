package Grazie.com.Grazie_Backend.member.controller;

import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.member.dto.FindIdRequest;
import Grazie.com.Grazie_Backend.member.dto.ResetPasswordRequest;
import Grazie.com.Grazie_Backend.member.dto.TempPasswordRequest;
import Grazie.com.Grazie_Backend.member.entity.PasswordToken;
import Grazie.com.Grazie_Backend.member.service.PasswordResetService;
import Grazie.com.Grazie_Backend.member.dto.UserDTO;
import Grazie.com.Grazie_Backend.member.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final PasswordResetService passwordResetService;
    private final UserService userService;


    @PostMapping("/request-temp-password")
    public ResponseEntity<?> requestTempPassword(@RequestBody TempPasswordRequest request) {
        try {
            passwordResetService.generateTempPassword(request);
            return ResponseEntity.ok("임시 비밀번호를 이메일로 발송했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("임시 비밀번호 발급 실패");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPasswordUsingTempPassword(request);
        return ResponseEntity.ok("비밀번호 변경 완료!");
    }

    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestBody FindIdRequest request) {
        try {
            userService.findId(request.getEmail());
            return ResponseEntity.ok("아이디가 해당 이메일로 발송되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
