package Grazie.com.Grazie_Backend.Config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static UserAdapter getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return (UserAdapter) authentication.getPrincipal();
        }
        throw new UnauthorizedException();
    }
}

