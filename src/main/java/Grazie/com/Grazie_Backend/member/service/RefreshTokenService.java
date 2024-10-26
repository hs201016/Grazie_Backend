package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.Config.JwtUtil;
import Grazie.com.Grazie_Backend.member.entity.RefreshToken;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


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

    public RefreshToken checkRefreshToken(User user) {
        return  refreshTokenRepository.findByUser(user);
    }

    public String validateAndRenewRefreshToken(User user) {
        RefreshToken existingToken = checkRefreshToken(user);

        if (existingToken != null) {
            if (existingToken.isRevoked() || existingToken.getExpiresAt().isBefore(LocalDateTime.now())) {
                System.out.println("기존 리프레시 토큰이 무효화되었거나 만료되었으므로 재발급합니다.");

                refreshTokenRepository.delete(existingToken);

                String newRefreshToken = jwtUtil.generateRefreshToken(user.getId());
                saveRefreshToken(user);
                return newRefreshToken;
            }
            System.out.println("기존 리프레시 토큰이 유효하므로 재발급하지 않습니다.");
            return existingToken.getToken();
        }
        // 리프레시 토큰이 없는 경우 새로 발급
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getId());
        saveRefreshToken(user);
        return newRefreshToken;
    }

    public RefreshToken findRefreshToken(String refreshToken) {
        Optional<RefreshToken> token = refreshTokenRepository.findByToken(refreshToken);

        if (token.isPresent()) {
            return token.get();
        } else {
            throw new RuntimeException("리프레시 토큰을 찾을 수 없습니다: " + refreshToken);
        }
    }
        public void checkRevokedToken (RefreshToken token){
            if (token.isRevoked()) {
                throw new RuntimeException("리프레시 토큰은 이미 무효화되었습니다.");
            }
        }

        public void setRevokeToken (RefreshToken token){
            token.setRevoked(true);
            refreshTokenRepository.save(token);
        }
    }
