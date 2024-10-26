package Grazie.com.Grazie_Backend.Order.exception;

// 상품을 찾을 수 없을 경우에 대한 예외
public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
