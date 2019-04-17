package fakenewsagency.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Article not found!")
public class ArticleNotFoundException extends RuntimeException {

    private int statusCode;

    public ArticleNotFoundException() {
        this.statusCode = 404;
    }

    public ArticleNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
