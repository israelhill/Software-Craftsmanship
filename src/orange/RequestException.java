package orange;

public class RequestException extends Exception {
    private RequestError requestException;

    public RequestException() {
        requestException = RequestError.BAD_REQUEST;
    }

    public enum RequestError {
        BAD_REQUEST
    }
}
