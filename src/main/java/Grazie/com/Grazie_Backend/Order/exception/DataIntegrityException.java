package Grazie.com.Grazie_Backend.Order.exception;

// 데이터 무결성 위반 시 발생하는 예외
public class DataIntegrityException extends RuntimeException{
    public DataIntegrityException(String message) {
        super(message);
    }

    public DataIntegrityException(String message, Throwable cause) {
        super(message, cause);
    }
}
