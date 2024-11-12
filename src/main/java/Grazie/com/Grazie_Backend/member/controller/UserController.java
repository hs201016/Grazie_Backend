package Grazie.com.Grazie_Backend.member.controller;

import Grazie.com.Grazie_Backend.Config.SecurityUtils;
import Grazie.com.Grazie_Backend.Config.UnauthorizedException;
import Grazie.com.Grazie_Backend.Config.UserAdapter;
import Grazie.com.Grazie_Backend.member.dto.PasswordDTO;
import Grazie.com.Grazie_Backend.member.dto.UserDTO;
import Grazie.com.Grazie_Backend.member.dto.UserJoinRequest;
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

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserJoinRequest request) {
            Long userId = userService.joinUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @GetMapping("/readProfile")
    public ResponseEntity<?> readUserProfile() {
        UserDTO userDTO = userService.readUser();
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UserDTO userDTO) {
        UserAdapter userAdapter = SecurityUtils.getCurrentUser();
        User user = userService.updateUser(userAdapter, userDTO);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/changeTempPassword")
    public ResponseEntity<?> verifyTempPassword(@RequestParam("token") String token,
                                                @RequestBody PasswordDTO passwordDTO) {
        userService.changeTempPassword(token, passwordDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDTO passwordDTO) {
        userService.updatePassword(passwordDTO);
        return ResponseEntity.ok().build();
    }
}