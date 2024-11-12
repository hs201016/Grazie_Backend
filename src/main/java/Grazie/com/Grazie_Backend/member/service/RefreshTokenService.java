package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.Config.JwtUtil;
import Grazie.com.Grazie_Backend.global.exception.AppException;
import Grazie.com.Grazie_Backend.member.entity.RefreshToken;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static Grazie.com.Grazie_Backend.global.util.ErrorCode.REFRESH_TOKEN_EXPIRE;
import static Grazie.com.Grazie_Backend.global.util.ErrorCode.REFRESH_TOKEN_NOT_FOUND;


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

                refreshTokenRepository.delete(existingToken);

                String newRefreshToken = jwtUtil.generateRefreshToken(user.getId());
                saveRefreshToken(user);
                return newRefreshToken;
            }
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
            throw new AppException(REFRESH_TOKEN_NOT_FOUND);
        }
    }
        public void checkRevokedToken (RefreshToken token){
            if (token.isRevoked()) {
                throw new AppException(REFRESH_TOKEN_EXPIRE);
            }
        }

        public void setRevokeToken (RefreshToken token){
            token.setRevoked(true);
            refreshTokenRepository.save(token);
        }
    }
