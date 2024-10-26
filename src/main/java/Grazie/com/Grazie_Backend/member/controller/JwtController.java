package Grazie.com.Grazie_Backend.member.controller;

import Grazie.com.Grazie_Backend.Config.JwtUtil;
import Grazie.com.Grazie_Backend.member.service.TokenManagementService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final TokenManagementService tokenManagementService;
    private final JwtUtil jwtUtil;


    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody String refreshToken) {
        return ResponseEntity.ok(tokenManagementService.refreshAccessToken(refreshToken));
    }


    @PostMapping("/extract-claims")
    public ResponseEntity<?> extractClaims(@RequestBody String token) {
        try {
            Claims claims = jwtUtil.extractAllClaims(token);
            String userId = claims.getSubject();

            Date expirationDate = claims.getExpiration();
            LocalDateTime expiresAt = LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault());

            if (expiresAt.isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(400).body("Refresh Token이 만료되었습니다!");
            }

            return ResponseEntity.ok(Map.of(
                    "userId", userId,
                    "expiresAt", expiresAt.toString()
            ));


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("유효하지 않은 토큰입니다.");
        }
    }
}
