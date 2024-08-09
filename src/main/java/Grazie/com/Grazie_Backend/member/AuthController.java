package Grazie.com.Grazie_Backend.member;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final TokenManagementService tokenManagementService;
    private final JwtUtil jwtUtil;

    private final UserJoinService userJoinService;

    @PostMapping("/join")
    public ResponseEntity<User> join(@RequestBody User user) {
        try {
            User savedUser = userJoinService.joinUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@RequestBody LoginRequestDTO request) {
        log.info("Login attempt with userId: {}", request.getUserid());
        return ResponseEntity.ok(authService.login(request.getUserid(), request.getPassword()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody String refreshToken) {
        return ResponseEntity.ok(tokenManagementService.refreshAccessToken(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody String refreshToken) {
        authService.logOut(refreshToken);
        return ResponseEntity.noContent().build(); // 204 에러 반환
    }

    @PostMapping("/extract-claims")
    public ResponseEntity<?> extractClaims(@RequestBody String token) {
        try {
            Claims claims = jwtUtil.extractAllClaims(token);
            String userId = claims.getSubject();

            Date expirationDate = claims.getExpiration();
            LocalDateTime expiresAt = LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault());

            // JWT 만료 확인
            if (expiresAt.isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(400).body("Refresh Token이 만료되었습니다!");
            }

            // 성공적으로 클레임을 추출한 경우
            return ResponseEntity.ok(Map.of(
                    "userId", userId,
                    "expiresAt", expiresAt.toString()
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("유효하지 않은 토큰입니다 여기인가?.");
        }
    }
}

