package Grazie.com.Grazie_Backend.Order.exception;

// 쿠폰을 찾을 수 없는 경우에 대한 예외
public class CouponNotFoundException extends RuntimeException{
    public CouponNotFoundException(String message) {
        super(message);
    }

    public CouponNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
