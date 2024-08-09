package Grazie.com.Grazie_Backend.member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;

import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;


    @Test
    void extractAllClaimsTest() {
        String accessToken = jwtUtil.generateAccessToken(2);
        Claims claims = jwtUtil.extractAllClaims(accessToken);
        System.out.println(claims);
        assertEquals("2", claims.getSubject());
    }

    @BeforeEach
    void setUp() {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String encodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        jwtUtil = new JwtUtil();
        jwtUtil.secretString = encodedSecretKey;
        jwtUtil.expiration = 1000L * 60 * 60; // 1 시간
        jwtUtil.refreshExpiration = 1000L * 60 * 60 * 24 * 7; // 1 주일

        // @PostConstruct 호출
        jwtUtil.init();
    }


    @Test
    void generateAccessTokenTest() {
        String accessToken = jwtUtil.generateAccessToken(1);
        Assertions.assertThat(accessToken).isNotNull();
    }

    @Test
    void generateRefreshTokenTest() {
        String refreshToken = jwtUtil.generateRefreshToken(1);
        Assertions.assertThat(refreshToken).isNotNull();
    }


    @Test
    void extractClaimTest() {
        String accessToken = jwtUtil.generateAccessToken(3);
        String subject = jwtUtil.extractClaim(accessToken, Claims::getSubject);
        assertEquals("3", subject);
    }

    @Test
    void extractExpirationTest() {
        long now = System.currentTimeMillis();


        long expectedAccessExpiration = now + jwtUtil.expiration;
        long expectedRefreshExpiration = now + jwtUtil.refreshExpiration;

        // 허용 오차 (밀리초 단위)
        long tolerance = 1000L;

        String accessToken4 = jwtUtil.generateAccessToken(4);
        String refreshToken4 = jwtUtil.generateRefreshToken(4);
        Date accessDate = jwtUtil.extractExpiration(accessToken4);
        Date refreshDate = jwtUtil.extractExpiration(refreshToken4);
        assertNotNull(accessDate);
        assertNotNull(refreshDate);

        // 검증: 만료 시간과 현재 시간의 차이를 허용 오차 범위 내에서 비교
        assertEquals(expectedAccessExpiration, accessDate.getTime(), tolerance, "엑세스 토큰 시간 안 맞음");
        assertEquals(expectedRefreshExpiration, refreshDate.getTime(), tolerance, "리프레시 토큰 시간 안 맞음");
    }
}