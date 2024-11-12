package Grazie.com.Grazie_Backend.Config;

import Grazie.com.Grazie_Backend.global.exception.AppException;
import Grazie.com.Grazie_Backend.global.util.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // AppException 처리
    @ExceptionHandler(AppException.class)
    public ResponseEntity<String> handleAppException(AppException e) {
        HttpStatus status = e.getErrorCode().getHttpStatus();
        String message = e.getErrorCode().getMessage();

        return ResponseEntity.status(status).body(message);
    }

    // UnauthorizedException 처리
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증받지 못한 사용자입니다.");
    }

}
