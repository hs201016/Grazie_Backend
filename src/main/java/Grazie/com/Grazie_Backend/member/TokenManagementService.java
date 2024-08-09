package Grazie.com.Grazie_Backend.member;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        LocalDateTime expiresAt = LocalDateTime.parse(claims.get("expiresAt", String.class));

        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh Token이 만료되었습니다!");
        }

        Optional<RefreshToken> token = refreshTokenRepository.findByToken(refreshToken);
        if (token.isEmpty() || token.get().isRevoked()) {
            throw new RuntimeException("유효하지 않거나 무효화 된 토큰입니다.");
        }

        return jwtUtil.generateRefreshToken(Long.parseLong(userId));
    }

    // 로그아웃 할때 토큰 철회
    public void revokeRefreshToken(String refreshToken) {
        Optional<RefreshToken> token = refreshTokenRepository.findByToken(refreshToken);
        if (token.isPresent()) {
            RefreshToken refreshToken1 = token.get();
            refreshToken1.setRevoked(true);
            refreshTokenRepository.save(refreshToken1);
        } else {
            throw new RuntimeException("리프레시 토큰을 찾을 수 없습니다.");
        }
    }
}
