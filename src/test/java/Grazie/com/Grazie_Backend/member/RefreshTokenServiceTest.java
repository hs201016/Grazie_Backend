package Grazie.com.Grazie_Backend.member;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import Grazie.com.Grazie_Backend.member.service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;


class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks // 인스턴스화 하고 모의 객체 주입
    private RefreshTokenService refreshTokenService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        refreshTokenService = new RefreshTokenService(refreshTokenRepository, jwtUtil);
    }

    @Test
    void saveRefreshToken() {
        User user = new User();
        user.setId(1L);

        String refreshToken = "testRefreshToken";
        when(jwtUtil.generateRefreshToken(user.getId())).thenReturn(refreshToken);
        when(jwtUtil.getRefreshExpiration()).thenReturn(3600L);

        // when
        refreshTokenService.saveRefreshToken(user);

        //then
        verify(refreshTokenRepository).save(any(RefreshToken.class));

        ArgumentCaptor<RefreshToken> captor = ArgumentCaptor.forClass(RefreshToken.class);
        verify(refreshTokenRepository).save(captor.capture());
        // 메서드가 호출되었음을 검증하고, 호출 시 전달된 인자를 captor로 캡처.

        RefreshToken savedToken = captor.getValue();
        assertNotNull(savedToken);
        assertEquals(refreshToken, savedToken.getToken());
        assertEquals(user, savedToken.getUser());
        assertTrue(savedToken.getIssuedAt().isBefore(LocalDateTime.now()));
        assertTrue(savedToken.getExpiresAt().isAfter(LocalDateTime.now()));

    }

    @Test
    void saveRefreshTokenTest() {
        User user = new User();
        user.setId(1L);
        refreshTokenService.saveRefreshToken(user);
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }


}