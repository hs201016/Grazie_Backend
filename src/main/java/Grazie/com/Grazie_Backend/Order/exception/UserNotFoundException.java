package Grazie.com.Grazie_Backend.Order.exception;

// 유저를 찾을 수 없을 경우에 대한 예외
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }
}
