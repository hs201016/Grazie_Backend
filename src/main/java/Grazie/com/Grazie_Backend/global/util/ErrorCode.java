package Grazie.com.Grazie_Backend.global.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import retrofit2.http.HTTP;

@RequiredArgsConstructor
public enum ErrorCode {

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 아이디입니다."),
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "%s는 이미 존재하는 아이디입니다."),
    ADDITIONAL_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 유저의 추가정보를 확인해주세요."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "%s는 이미 존재하는 이메일 입니다."),
    ID_EMAIL_NOT_MATCH(HttpStatus.CONFLICT, "사용자 ID에 해당하는 이메일이 일치하지 않습니다."),
    PHONE_ALREADY_EXISTS(HttpStatus.CONFLICT, "%s는 이미 존재하는 전화 번호입니다."),
    EMAIL_NOT_FOUND(HttpStatus.CONFLICT, "입력하신 이메일 '%s'에 해당하는 사용자가 존재하지 않습니다."),
    PASSWORD_NOT_VALID(HttpStatus.BAD_REQUEST, "비밀번호가 유효하지 않습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다."),
    PASSWORD_NOT_NULL(HttpStatus.BAD_REQUEST, "비밀번호는 null 일 수 없습니다."),
    TEMP_PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "임시 비밀번호가 맞지 않습니다."),
    INVALID_CURRENT_PASSWORD(HttpStatus.BAD_REQUEST, "현재 비밀번호가 올바르지 않습니다."),
    IMAGE_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장에 실패하였습니다. 다시 시도해 주세요."),
    INVALID_GENDER(HttpStatus.BAD_REQUEST, "유효하지 않은 성별값입니다."),

    // JWT  REFRESH TOKEN
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 리프레시 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "리프레시 토큰을 찾을 수 없습니다"),
    REFRESH_TOKEN_EXPIRE(HttpStatus.BAD_REQUEST, "리프레시 토큰이 만료되었습니다."),

    // JWT TOKEN
    ERROR_TOKEN(HttpStatus.NOT_FOUND, "만료됬거나 토큰을 찾을 수 없습니다."),
    TOKEN_EXPIRE(HttpStatus.BAD_REQUEST, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.NOT_FOUND, "유효하지 않은 토큰입니다."),
    TOKEN_SIGNATURE_INVALID(HttpStatus.UNAUTHORIZED, "서명 검증에 실패했습니다. 토큰이 변조되었을 수 있습니다."),

    ;


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
