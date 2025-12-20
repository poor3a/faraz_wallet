package faraz.wallet.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handle(ApiException ex) {

        ApiErrorResponse response =
                new ApiErrorResponse(ex.getStatus().value(), ex.getMessage());

        return ResponseEntity
                .status(ex.getStatus())
                .body(response);
    }
}
