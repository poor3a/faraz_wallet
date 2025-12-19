package faraz.wallet.exception;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
@NoArgsConstructor
public class ApiException extends RuntimeException {

    private  HttpStatus status;
    private  Object[] args;

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

}
