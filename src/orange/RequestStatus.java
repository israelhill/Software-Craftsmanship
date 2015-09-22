package orange;

import java.math.BigInteger;
import java.util.Optional;

public class RequestStatus {
    private StatusCode statusCode;
    private Optional<BigInteger> result;

    public RequestStatus() {
        statusCode = StatusCode.UNKNOWN;
    }

    public enum StatusCode {
        UNKNOWN,
        OK,
        FAIL;
    }

    public void setStatusCode(StatusCode code) {
        statusCode = code;
    }

    public void setResult(Optional<BigInteger> result) {
        this.result = result;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public Optional<BigInteger> getResult() {
        return result;
    }
}
