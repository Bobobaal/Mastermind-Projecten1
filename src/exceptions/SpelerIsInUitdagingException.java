package exceptions;

/**
 * Exception die wordt opgegooid als de speler al een openstaande uitdaging heeft.
 *
 * @author groep 31
 */
public class SpelerIsInUitdagingException extends Exception {

    public SpelerIsInUitdagingException() {
    }

    public SpelerIsInUitdagingException(String message) {
        super(message);
    }

    public SpelerIsInUitdagingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpelerIsInUitdagingException(Throwable cause) {
        super(cause);
    }

    public SpelerIsInUitdagingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
