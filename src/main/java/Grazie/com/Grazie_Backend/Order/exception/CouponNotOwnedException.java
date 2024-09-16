package Grazie.com.Grazie_Backend.Order.exception;

// 사용자가 쿠폰을 소유하지 않은 경우에 대한 예외
public class CouponNotOwnedException extends RuntimeException{
    public CouponNotOwnedException(String message) {
        super(message);
    }

    public CouponNotOwnedException(String message, Throwable cause) {
        super(message, cause);
    }
}
