package Grazie.com.Grazie_Backend.Order.exception;

// 매장에서 판매하지 않는 상품에 대한 예외
public class ProductNotSoldException extends RuntimeException{
    public ProductNotSoldException(String message) {
        super(message);
    }

    public ProductNotSoldException(String message, Throwable cause) {
        super(message, cause);
    }
}
