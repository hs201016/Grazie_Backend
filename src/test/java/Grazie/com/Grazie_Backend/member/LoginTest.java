package Grazie.com.Grazie_Backend.member;

import Grazie.com.Grazie_Backend.Config.JwtUtil;
import Grazie.com.Grazie_Backend.member.dto.LoginResponseDTO;
import Grazie.com.Grazie_Backend.member.entity.User;
import Grazie.com.Grazie_Backend.member.repository.RefreshTokenRepository;
import Grazie.com.Grazie_Backend.member.repository.UserRepository;
import Grazie.com.Grazie_Backend.member.service.AuthService;
import Grazie.com.Grazie_Backend.member.service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LoginTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @MockBean
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Success() {
        // Given
        Long id = 1L;
        String userId = "testUser";
        String password = "password123";
        String accessToken = "accessToken123";
        String refreshToken = "refreshToken123";

        User user = new User();
        user.setId(id);
        user.setUserId(userId);
        user.setPassword(password);

        // Mock 설정 확인
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(jwtUtil.generateAccessToken(id)).thenReturn(accessToken);
        when(jwtUtil.generateRefreshToken(id)).thenReturn(refreshToken);

        // When
        LoginResponseDTO response = authService.login(userId, password);

        // Then
        assertEquals(accessToken, response.getAccessToken());
        assertEquals(refreshToken, response.getRefreshToken());
        verify(refreshTokenService).saveRefreshToken(user);
    }
}





