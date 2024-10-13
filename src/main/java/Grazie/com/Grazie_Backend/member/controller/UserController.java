package Grazie.com.Grazie_Backend.member.controller;

import Grazie.com.Grazie_Backend.Config.SecurityUtils;
import Grazie.com.Grazie_Backend.Config.UnauthorizedException;
import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.member.dto.PasswordDTO;
import Grazie.com.Grazie_Backend.member.dto.UserDTO;
import Grazie.com.Grazie_Backend.member.service.UserService;
import Grazie.com.Grazie_Backend.member.entity.PasswordToken;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.PasswordTokenRepository;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody User user) {
        try {
            Long userId = userService.joinUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/readProfile")
    public ResponseEntity<?> readUserProfile() {
        UserAdapter userAdapter = SecurityUtils.getCurrentUser();
        if (userAdapter == null) {
            throw new UnauthorizedException();
        }

        UserDTO userDTO = userService.readUser(userAdapter);
        return ResponseEntity.ok(userDTO);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UserDTO userDTO) {
        UserAdapter userAdapter = SecurityUtils.getCurrentUser();
        if (userAdapter == null) {
            throw new UnauthorizedException();
        }

        User user = userService.updateUser(userAdapter, userDTO);
        return ResponseEntity.ok(user);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser() {
        UserAdapter userAdapter = SecurityUtils.getCurrentUser();
        if (userAdapter == null) {
            throw new UnauthorizedException();
        }

        userService.deleteUser(userAdapter);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/changeTempPassword")
    public ResponseEntity<?> verifyTempPassword(@RequestParam("token") String token,
                                                @RequestBody PasswordDTO passwordDTO) {
        PasswordToken passwordToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("유효하지 않은 토큰임"));

        if (passwordToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료되었음");
        }

        User user = passwordToken.getUser();
        String newPassword = passwordDTO.getNewPassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(passwordToken);

        return ResponseEntity.ok("비밀번호 변경 ok");
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDTO passwordDTO) {
        UserAdapter userAdapter = SecurityUtils.getCurrentUser();
        if (userAdapter == null) {
            throw new UnauthorizedException();
        }

        userService.updatePassword(userAdapter, passwordDTO);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }
}