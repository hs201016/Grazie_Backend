package Grazie.com.Grazie_Backend.Store.exception;

public class MissingRequiredFieldException extends RuntimeException{
    public MissingRequiredFieldException(String message) {
        super(message);
    }

    public MissingRequiredFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
