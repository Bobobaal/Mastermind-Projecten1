package exceptions;

/**
 * Exception die wordt opgegooid als een speler (nog) niet bestaat.
 *
 * @author groep 31
 */
public class SpelerBestaatNietException extends Exception {

    public SpelerBestaatNietException() {
    }

    public SpelerBestaatNietException(String message) {
        super(message);
    }

    public SpelerBestaatNietException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpelerBestaatNietException(Throwable cause) {
        super(cause);
    }

    public SpelerBestaatNietException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
