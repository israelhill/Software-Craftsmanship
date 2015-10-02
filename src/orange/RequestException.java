package orange;

public class RequestException extends Exception {
    private RequestError requestException;
    private Exception cause;
    private String message;

    /**
     * Constructor for the class
     * Sets error to the only defined error for now
     */
    public RequestException(String message, Exception cause) {
        requestException = RequestError.BAD_REQUEST;
        this.cause = cause;
        this.message = message;
    }

    public RequestException() {
        requestException = RequestError.BAD_REQUEST;
    }

    /**
     * Enum containing different types of errors.
     * For now, there is only one error.
     * This will likely change.
     */
    public enum RequestError {
        BAD_REQUEST
    }
}
