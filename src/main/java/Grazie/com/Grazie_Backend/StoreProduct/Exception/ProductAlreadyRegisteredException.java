package Grazie.com.Grazie_Backend.StoreProduct.Exception;

public class ProductAlreadyRegisteredException extends RuntimeException{
    public ProductAlreadyRegisteredException(String message) {
        super(message);
    }

    public ProductAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
