package Grazie.com.Grazie_Backend.Order.exception;

// 판매 중지된 상품에 대한 예외
public class ProductDiscontinuedException extends RuntimeException{
    public ProductDiscontinuedException(String message) {
        super(message);
    }

    public ProductDiscontinuedException(String message, Throwable cause) {
        super(message, cause);
    }
}
