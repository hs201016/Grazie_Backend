package Grazie.com.Grazie_Backend.member.controller;

import Grazie.com.Grazie_Backend.member.service.PasswordResetService;
import Grazie.com.Grazie_Backend.member.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/request-temp-password")
    public ResponseEntity<?> requestTempPassword(@RequestBody UserDTO userDTO) {
        try {
            String email = userDTO.getEmail();
            passwordResetService.generateTempPassword(email);
            return ResponseEntity.ok("임시 비밀번호를 이메일로 발송했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("임시 비밀번호 발급 실패");
        }
    }
}
