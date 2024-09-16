package Grazie.com.Grazie_Backend.Store.exception;

public class StoreAlreadyRegisteredException extends RuntimeException{
    public StoreAlreadyRegisteredException(String message) {
        super(message);
    }

    public StoreAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
