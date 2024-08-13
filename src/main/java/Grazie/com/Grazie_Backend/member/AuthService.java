package Grazie.com.Grazie_Backend.member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        refreshTokenService.saveRefreshToken(user);

        return new LoginResponseDTO(accessToken, refreshToken);
    }

    // RefreshToken -> BlackList
    public void logOut(String refreshToken) {
        Optional<RefreshToken> token = refreshTokenRepository.findByToken(refreshToken);
        if (token.isPresent()) {
            RefreshToken refreshToken1 = new RefreshToken();
            refreshToken1.setRevoked(true);
            refreshTokenRepository.save(refreshToken1);
        } else
            throw new RuntimeException("리프레시 토큰을 찾을 수 없습니다!");
    }


}
