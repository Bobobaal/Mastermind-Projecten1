package exceptions;

/**
 * Exception die wordt opgegooid als de bevestiging van een wachtwoord faalt.
 *
 * @author groep 31
 */
public class WachtwoordBevestigingException extends Exception {

    public WachtwoordBevestigingException() {
    }

    public WachtwoordBevestigingException(String message) {
        super(message);
    }

    public WachtwoordBevestigingException(String message, Throwable cause) {
        super(message, cause);
    }

    public WachtwoordBevestigingException(Throwable cause) {
        super(cause);
    }

    public WachtwoordBevestigingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
