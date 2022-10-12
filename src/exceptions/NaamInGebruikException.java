package exceptions;

/**
 * Exception die wordt opgegooid als de naam van een speler al in gebruik is.
 *
 * @author groep 31
 */
public class NaamInGebruikException extends Exception {

    public NaamInGebruikException() {
    }

    public NaamInGebruikException(String message) {
        super(message);
    }

    public NaamInGebruikException(String message, Throwable cause) {
        super(message, cause);
    }

    public NaamInGebruikException(Throwable cause) {
        super(cause);
    }

    public NaamInGebruikException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
