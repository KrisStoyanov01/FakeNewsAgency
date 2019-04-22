package fakenewsagency.domain.models.view;

import fakenewsagency.domain.entites.Article;
import fakenewsagency.domain.entites.ArticleCategory;
import fakenewsagency.domain.entites.Comment;
import fakenewsagency.domain.entites.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CommentListViewModel {
    private String id;
    private Article articleOwner;
    private User author;
    private String content;
    private LocalDate createdOn;

    public CommentListViewModel() {
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
