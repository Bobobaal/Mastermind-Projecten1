package exceptions;

/**
 * Exception die wordt opgegooid als een verkeerd wachtwoord wordt ingegeven.
 *
 * @author groep 31
 */
public class VerkeerdWachtwoordException extends Exception {

    public VerkeerdWachtwoordException() {
    }

    public VerkeerdWachtwoordException(String message) {
        super(message);
    }

    public VerkeerdWachtwoordException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerkeerdWachtwoordException(Throwable cause) {
        super(cause);
    }

    public VerkeerdWachtwoordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
