package Grazie.com.Grazie_Backend.member.controller;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/join")
    public ResponseEntity<User> join(@RequestBody User user) {
        try {
            User savedUser = userService.joinUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/readProfile")
    public ResponseEntity<?> readUserProfile(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            UserDTO userDTO = userService.readUser(jwtToken);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("기본 프로필  조회 중 오류 발생");

        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UserDTO userDTO, @RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            User user = userService.updateUser(jwtToken, userDTO);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("기본 프로필 업데이트 중 오류 발생");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        userService.deleteUser(jwtToken);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/changeTempPassword")
    public ResponseEntity<?> verifyTempPassword(@RequestParam("token") String token,
                                                  @RequestBody PasswordDTO passwordDTO) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 변경 실패!");
        }
    }

    @PutMapping("changePassword")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, @RequestBody PasswordDTO passwordDTO) {
        try {
            String jwtToken = token.substring(7);
            User user = userService.updatePassword(jwtToken, passwordDTO);
            return ResponseEntity.ok("비밀번호가 변경되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 변경 중 오류 발생");
        }
    }
}
