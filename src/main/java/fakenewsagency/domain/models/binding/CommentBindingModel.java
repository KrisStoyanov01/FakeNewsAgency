package fakenewsagency.domain.models.binding;

import fakenewsagency.domain.entites.Article;
import fakenewsagency.domain.entites.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CommentBindingModel {
    private String id;
    private Article articleOwner;
    private User author;
    private String content;
    private LocalDate createdOn;

    public CommentBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Article getArticleOwner() {
        return articleOwner;
    }

    public void setArticleOwner(Article articleOwner) {
        this.articleOwner = articleOwner;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @NotNull(message = "Content cannot be null.")
    @Length(min = 5, max = 100, message = "Content should be between 5 and 100 symbols.")
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
}
