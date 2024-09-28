package Grazie.com.Grazie_Backend.Config;

public enum ErrorMessage {
    UNAUTHORIZED("인증되지 않은 사용자입니다."),
    USER_NOT_FOUND("사용자가 존재하지 않습니다."),
    BAD_REQUEST("잘못된 요청입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

