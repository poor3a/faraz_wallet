package faraz.wallet.exception;

import lombok.Getter;

@Getter
public class ApiSubError {

    private final String field;
    private final String message;

    public ApiSubError(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
