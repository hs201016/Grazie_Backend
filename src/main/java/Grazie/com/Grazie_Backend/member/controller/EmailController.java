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
    public ResponseEntity<Void> requestTempPassword(@RequestBody TempPasswordRequest request) {
        passwordResetService.generateTempPassword(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPasswordUsingTempPassword(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/find-id")
    public ResponseEntity<Void> findId(@RequestBody FindIdRequest request) {
        userService.findId(request.getEmail());
        return ResponseEntity.ok().build();
    }

}
