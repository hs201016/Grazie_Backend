package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.Config.JwtUtil;
import Grazie.com.Grazie_Backend.global.exception.AppException;
import Grazie.com.Grazie_Backend.global.util.ErrorCode;
import Grazie.com.Grazie_Backend.member.dto.LoginResponse;
import Grazie.com.Grazie_Backend.member.entity.RefreshToken;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.RefreshTokenRepository;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static Grazie.com.Grazie_Backend.global.util.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(String userid, String password) {
        User user = userRepository.findByUserId(userid)
                .orElseThrow(() -> new AppException(INVALID_USER_ID));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AppException(PASSWORD_NOT_MATCH);
        }

        String accessToken = jwtUtil.generateAccessToken(user.getId());
        String refreshToken = refreshTokenService.validateAndRenewRefreshToken(user);

        return new LoginResponse(accessToken, refreshToken);
    }

    public void logOut(String refreshToken) {
        RefreshToken existToken = refreshTokenService.findRefreshToken(refreshToken);
        refreshTokenService.checkRevokedToken(existToken);
        refreshTokenService.setRevokeToken(existToken);
    }
}
