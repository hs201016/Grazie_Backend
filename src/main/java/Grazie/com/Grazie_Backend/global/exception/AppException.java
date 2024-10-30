package Grazie.com.Grazie_Backend.global.exception;

import Grazie.com.Grazie_Backend.global.util.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AppException extends RuntimeException{

    private final ErrorCode errorCode;

    @Override
    public String toString() {
        return errorCode.getMessage();
    }
}
