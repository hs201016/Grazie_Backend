package Grazie.com.Grazie_Backend.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtRequestInterceptor jwtRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtRequestInterceptor)
                .addPathPatterns("/cart/**")  // 장바구니 관련 JWT 인증 적용
                .addPathPatterns("/api/coupons/**"); // 쿠폰 관련
    }
}
