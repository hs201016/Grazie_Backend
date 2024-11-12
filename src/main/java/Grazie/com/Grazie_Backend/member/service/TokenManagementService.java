package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.Config.JwtUtil;
import Grazie.com.Grazie_Backend.global.exception.AppException;
import Grazie.com.Grazie_Backend.global.util.ErrorCode;
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

import static Grazie.com.Grazie_Backend.global.util.ErrorCode.*;


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
            throw new AppException(REFRESH_TOKEN_EXPIRE);
        }

        Optional<RefreshToken> token = refreshTokenRepository.findByToken(refreshToken);
        if (token.isEmpty() || token.get().isRevoked()) {
            throw new AppException(ERROR_TOKEN);
        }

        return jwtUtil.generateRefreshToken(Long.parseLong(userId));
    }
}
