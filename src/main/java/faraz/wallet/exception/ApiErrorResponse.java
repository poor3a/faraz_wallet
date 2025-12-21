package faraz.wallet.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

    private final Instant timestamp = Instant.now();
    private final int status;
    private final String error;
    private final String message;
    private final List<ApiSubError> errors;

    public ApiErrorResponse(
            int status,
            String error,
            String message,
            List<ApiSubError> errors
    ) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorResponse(int status, String error, String message) {
        this(status, error, message, null);
    }
}
