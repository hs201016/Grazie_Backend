package Grazie.com.Grazie_Backend.member.controller;

import Grazie.com.Grazie_Backend.member.service.AuthService;
import Grazie.com.Grazie_Backend.member.dto.LoginRequestDTO;
import Grazie.com.Grazie_Backend.member.dto.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request.getUserid(), request.getPassword()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody Map<String, String> payload) {
        String refreshToken = payload.get("refreshToken");
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().build(); // 잘못된 요청 처리
        }
        authService.logOut(refreshToken);
        System.out.println("님 로그아웃됨");
        return ResponseEntity.noContent().build();
    }
}


