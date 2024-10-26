package Grazie.com.Grazie_Backend.Order.exception;

// 쿠폰이 만료된 경우에 대한 예외
public class CouponExpiredException extends RuntimeException{
    public CouponExpiredException(String message) {
        super(message);
    }

    public CouponExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
