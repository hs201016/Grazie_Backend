package Grazie.com.Grazie_Backend.Product.exception;

public class InvalidFieldValueException extends RuntimeException {
    public InvalidFieldValueException(String message) {
        super(message);
    }

    public InvalidFieldValueException(String message, Throwable cause) {
        super(message, cause);
    }
}

