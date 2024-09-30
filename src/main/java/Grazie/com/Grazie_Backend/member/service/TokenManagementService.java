package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.Config.JwtUtil;
import Grazie.com.Grazie_Backend.member.entity.RefreshToken;
import Grazie.com.Grazie_Backend.member.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TokenManagementService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public String refreshAccessToken(String refreshToken) {
        Claims claims = jwtUtil.extractAllClaims(refreshToken);
        String userId = claims.getSubject(); // (Long) id 인 pk 값을  -> String 으로 치환

        Instant expiresAtInstant = Instant.parse(claims.get("expiresAt", String.class));
        LocalDateTime expiresAt = LocalDateTime.ofInstant(expiresAtInstant, ZoneOffset.UTC);

        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh Token이 만료되었습니다!");
        }

        Optional<RefreshToken> token = refreshTokenRepository.findByToken(refreshToken);
        if (token.isEmpty() || token.get().isRevoked()) {
            throw new RuntimeException("유효하지 않거나 무효화 된 토큰입니다.");
        }

        return jwtUtil.generateRefreshToken(Long.parseLong(userId));
    }
}
