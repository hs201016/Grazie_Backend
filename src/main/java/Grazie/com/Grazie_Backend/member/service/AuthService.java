package Grazie.com.Grazie_Backend.member.service;

import Grazie.com.Grazie_Backend.Config.JwtUtil;
import Grazie.com.Grazie_Backend.member.dto.LoginResponseDTO;
import Grazie.com.Grazie_Backend.member.entity.RefreshToken;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.RefreshTokenRepository;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(String userid, String password) {
        User user = userRepository.findByUserId(userid)
                .orElseThrow(() -> new RuntimeException("유효하지 않은 아이디입니다. "));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치 하지 않습니다.");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getId());
        String refreshToken = refreshTokenService.validateAndRenewRefreshToken(user);

        return new LoginResponseDTO(accessToken, refreshToken);
    }

    public void logOut(String refreshToken) {
        try {
            RefreshToken existToken = refreshTokenService.findRefreshToken(refreshToken);
            refreshTokenService.checkRevokedToken(existToken);
            refreshTokenService.setRevokeToken(existToken);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
