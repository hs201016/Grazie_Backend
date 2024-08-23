package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.Config.JwtUtil;
import Grazie.com.Grazie_Backend.member.entity.RefreshToken;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public void saveRefreshToken(User user) {
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUser(user);
        refreshTokenEntity.setIssuedAt(LocalDateTime.now());
        refreshTokenEntity.setExpiresAt(LocalDateTime.now().plusSeconds(jwtUtil.getRefreshExpiration()));

        refreshTokenRepository.save(refreshTokenEntity);
    }

}
