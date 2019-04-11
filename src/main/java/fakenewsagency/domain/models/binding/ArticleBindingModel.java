package fakenewsagency.domain.models.binding;

import fakenewsagency.domain.entites.ArticleCategory;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ArticleBindingModel {
    private String id;
    private String title;
    private ArticleCategory articleCategory;
    private String content;
    private LocalDate createdOn;

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

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }
}
