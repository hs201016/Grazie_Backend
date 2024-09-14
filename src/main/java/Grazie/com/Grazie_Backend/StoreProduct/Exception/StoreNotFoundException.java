package Grazie.com.Grazie_Backend.StoreProduct.Exception;

public class StoreNotFoundException extends RuntimeException {
    public StoreNotFoundException(String message) {
        super(message);
    }

    public StoreNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
