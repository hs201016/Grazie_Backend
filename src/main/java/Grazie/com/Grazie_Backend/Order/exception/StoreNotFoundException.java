package Grazie.com.Grazie_Backend.Order.exception;

// 매장을 찾을 수 없을 경우에 대한 예외
public class StoreNotFoundException extends RuntimeException{
    public StoreNotFoundException(String message) {
        super(message);
    }

    public StoreNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
