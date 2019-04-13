package fakenewsagency.domain.models.binding;

import fakenewsagency.domain.entites.ArticleCategory;

import fakenewsagency.domain.entites.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ArticleBindingModel {
    private String id;
    private String title;
    private User author;
    private ArticleCategory articleCategory;
    private String content;
    private LocalDate createdOn;
    private Integer views;

    public ArticleBindingModel() {
    }

    @NotNull(message = "Title cannot be null.")
    @Length(min = 5, max = 100, message = "Title should be between 5 and 100 symbols.")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @NotNull(message = "Category cannot be null.")
    public ArticleCategory getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(ArticleCategory articleCategory) {
        this.articleCategory = articleCategory;
    }

    @NotNull(message = "Content cannot be null.")
    @Length(min = 10, message = "Minimal length is 10 symbols.")
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

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}
