package Grazie.com.Grazie_Backend.member.controller;

import Grazie.com.Grazie_Backend.Config.JwtUtil;
import Grazie.com.Grazie_Backend.member.service.TokenManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt")
public class JwtController {

    private final TokenManagementService tokenManagementService;
    private final JwtUtil jwtUtil;


    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody String refreshToken) {
        return ResponseEntity.ok(tokenManagementService.refreshAccessToken(refreshToken));
    }

    @PostMapping("/extract-claims")
    public ResponseEntity<Map<String, Object>> extractClaims(@RequestBody String token) {
        Map<String, Object> claims = jwtUtil.extractClaims(token);
        return ResponseEntity.ok(claims);
    }
}
