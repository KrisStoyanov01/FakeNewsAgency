package fakenewsagency.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Group not found!")
public class GroupNotFoundException extends RuntimeException {

    private int statusCode;

    public GroupNotFoundException() {
        this.statusCode = 404;
    }

    public GroupNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
