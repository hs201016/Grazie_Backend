package Grazie.com.Grazie_Backend.member.controller;

import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.member.service.PasswordResetService;
import Grazie.com.Grazie_Backend.member.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/request-temp-password")
    public ResponseEntity<?> requestTempPassword(@AuthenticationPrincipal UserAdapter userAdapter) {
        try {
            String email = userAdapter.getUser().getEmail(); // JWT에서 이메일 가져오기
            passwordResetService.generateTempPassword(email);
            return ResponseEntity.ok("임시 비밀번호를 이메일로 발송했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("임시 비밀번호 발급 실패");
        }
    }
}
