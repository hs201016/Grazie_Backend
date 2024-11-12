package Grazie.com.Grazie_Backend.Config;

import Grazie.com.Grazie_Backend.global.exception.AppException;
import Grazie.com.Grazie_Backend.global.util.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static Grazie.com.Grazie_Backend.global.util.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final CustomUserDetails customUserDetails;

    @Value("${jwt.secret}")
    public String secretString;
    private SecretKey secretKey;

    @Value("${jwt.expiration}")
    long expiration;

    @Value("${jwt.refreshExpiration}")
    long refreshExpiration;


    @PostConstruct
    public void init() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretString);
            this.secretKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
        } catch (IllegalArgumentException e) {
        }
    }

    public String generateAccessToken(long userId) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userId, expiration);
    }

    public String generateRefreshToken(long userId) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userId, refreshExpiration);
    }


    private String createToken(Map<String, Object> claim, long userId, long expiration) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        claim.put("expiresAt", expirationDate.toInstant().toString());

        return Jwts.builder()
                .setClaims(claim)
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (io.jsonwebtoken.SignatureException e) {
            throw new AppException(TOKEN_SIGNATURE_INVALID);
        } catch (Exception e) {
            throw new AppException(INVALID_TOKEN);
        }
    }


    public Map<String, Object> extractClaims(String token) {
        Claims claims = extractAllClaims(token);
        String userId = claims.getSubject();
        Date expirationDate = claims.getExpiration();
        LocalDateTime expiresAt = LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault());

        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new AppException(TOKEN_EXPIRE);
        }
        return Map.of(
                "userId", userId,
                "expiresAt", expiresAt.toString()
        );
    }


    public long getRefreshExpiration() {
        return refreshExpiration;
    }

    public Authentication getAuthentication(String accessToken) {

        Claims claims = extractAllClaims(accessToken);

        String username = claims.getSubject();

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        // 인증 객체 생성
        return new UsernamePasswordAuthenticationToken(userDetails, accessToken, userDetails.getAuthorities());
    }
}
