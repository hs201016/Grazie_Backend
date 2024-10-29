package Grazie.com.Grazie_Backend.global.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ErrorCode {

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 아이디입니다."),
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "%s는 이미 존재하는 아이디입니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "%s는 이미 존재하는 이메일 입니다."),
    PHONE_ALREADY_EXISTS(HttpStatus.CONFLICT, "%s는 이미 존재하는 전화 번호입니다."),
    EMAIL_NOT_FOUND(HttpStatus.CONFLICT, "입력하신 이메일 '%s'에 해당하는 사용자가 존재하지 않습니다."),

    PASSWORD_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호가 유효하지 않습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다."),
    PASSWORD_NOT_NULL(HttpStatus.BAD_REQUEST, "비밀번호는 null 일 수 없습니다."),
    INVALID_CURRENT_PASSWORD(HttpStatus.BAD_REQUEST, "현재 비밀번호가 올바르지 않습니다."),

    // JWT TOKEN
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 리프레시 토큰입니다."),;


    // Personal Option

    // Cart

    // Coupon

    // Pay

    // Order

    @Getter
    private final HttpStatus httpStatus;
    private final String message;

    private String formattedMessage;

    public ErrorCode withArgs(Object... args) {
        this.formattedMessage = String.format(this.message, args);
        return this;
    }

    public String getMessage() {
        if(formattedMessage == null) return message;
        return formattedMessage;
    }
}
