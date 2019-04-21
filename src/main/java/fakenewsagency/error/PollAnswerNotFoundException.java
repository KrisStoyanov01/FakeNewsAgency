package fakenewsagency.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Poll Answer not found!")
public class PollAnswerNotFoundException extends RuntimeException {

    private int statusCode;

    public PollAnswerNotFoundException() {
        this.statusCode = 404;
    }

    public PollAnswerNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
