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
        // 에러 코드에 따라 상태 코드와 메시지 설정하기
        HttpStatus status = e.getErrorCode().getHttpStatus();
        String message = e.getErrorCode().getMessage();

        // 에러 응답을 생성하여 반환하기
        return ResponseEntity.status(status).body(message);
    }
}
