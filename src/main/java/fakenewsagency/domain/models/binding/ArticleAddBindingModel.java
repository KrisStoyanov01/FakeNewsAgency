package fakenewsagency.domain.models.binding;

import fakenewsagency.domain.entites.ArticleCategory;
import fakenewsagency.domain.entites.Comment;
import fakenewsagency.domain.entites.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ArticleAddBindingModel {
    private String id;
    private String title;
    private ArticleCategory articleCategory;
    private Integer views;
    private User author;
    private String content;
    private LocalDate createdOn;
    private Set<Comment> comments;

    public ArticleAddBindingModel() {
        this.comments = new HashSet<>();
    }

    public String getId() {
        return id;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
