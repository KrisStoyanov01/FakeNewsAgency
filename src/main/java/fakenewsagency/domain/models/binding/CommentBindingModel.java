package fakenewsagency.domain.models.binding;

import fakenewsagency.domain.entites.Article;
import fakenewsagency.domain.entites.User;

import java.time.LocalDate;

public class CommentBindingModel {
    private String id;
    private Integer score;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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
