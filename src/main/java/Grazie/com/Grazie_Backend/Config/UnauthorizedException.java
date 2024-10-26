package Grazie.com.Grazie_Backend.Config;

import lombok.Data;

@Data
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super(ErrorMessage.UNAUTHORIZED.getMessage());
    }
}