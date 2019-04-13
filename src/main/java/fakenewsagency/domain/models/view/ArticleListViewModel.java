package fakenewsagency.domain.models.view;

import fakenewsagency.domain.entites.ArticleCategory;
import fakenewsagency.domain.entites.User;

import java.time.LocalDate;

public class ArticleListViewModel {
    private String id;
    private User author;
    private String title;
    private ArticleCategory articleCategory;
    private Integer views;
    private LocalDate createdOn;

    public ArticleListViewModel() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArticleCategory getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(ArticleCategory articleCategory) {
        this.articleCategory = articleCategory;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }
}
