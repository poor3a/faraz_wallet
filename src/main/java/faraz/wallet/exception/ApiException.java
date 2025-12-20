package faraz.wallet.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final Object[] args;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.args = null;
    }

    public ApiException(HttpStatus status, String message, Object... args) {
        super(message);
        this.status = status;
        this.args = args;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Object[] getArgs() {
        return args;
    }
}
