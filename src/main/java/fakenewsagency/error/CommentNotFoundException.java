package fakenewsagency.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Comment not found!")
public class CommentNotFoundException extends RuntimeException {

    private int statusCode;

    public CommentNotFoundException() {
        this.statusCode = 404;
    }

    public CommentNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
