package exceptions;

/**
 * Exception die wordt opgegooid als een verplicht veld niet of verkeerd wordt ingevuld.
 *
 * @author groep 31
 */
public class VerplichtVeldException extends Exception {
    
    public VerplichtVeldException() {
    }
    
    public VerplichtVeldException(String message) {
        super(message);
    }

    public VerplichtVeldException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerplichtVeldException(Throwable cause) {
        super(cause);
    }

    public VerplichtVeldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
