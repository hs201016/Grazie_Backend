package Grazie.com.Grazie_Backend.member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {


    @Value("${jwt.secret}")
    public String secretString;
    private SecretKey secretKey;

    @Value("${jwt.expiration}")
    long expiration;

    @Value("${jwt.refreshExpiration}")
    long refreshExpiration;

    public JwtUtil() {
    }

    @PostConstruct
    public void init() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretString);
            this.secretKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
        }   catch (IllegalArgumentException e) {
        }
    }

    public String generateAccessToken(long userId) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userId, expiration);
    }

    public String generateRefreshToken(long userId) {
        Map<String, Object> claims = new HashMap<>();
        log.info("SecretKey: {}", secretKey);
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
            throw new RuntimeException("서명 검증에 실패했습니다. 토큰이 변조되었을 수 있습니다.", e);
        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 토큰입니다.", e);
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public long getRefreshExpiration() {
        return refreshExpiration;
    }



    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
