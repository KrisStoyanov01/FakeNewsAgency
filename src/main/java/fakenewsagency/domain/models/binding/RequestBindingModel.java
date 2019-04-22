package fakenewsagency.domain.models.binding;

import fakenewsagency.domain.entites.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class RequestBindingModel {
    private String id;
    private User author;
    private String content;
    private LocalDate createdOn;
    private String title;

    public RequestBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @NotNull(message = "Title cannot be null.")
    @Length(min = 5, max = 100, message = "Title should be between 5 and 100 symbols.")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
