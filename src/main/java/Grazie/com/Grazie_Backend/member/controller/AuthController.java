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

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {

        return ResponseEntity.ok(authService.login(request.getUserid(), request.getPassword()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody String refreshToken) {
        authService.logOut(refreshToken);
        return ResponseEntity.noContent().build();
    }
}


