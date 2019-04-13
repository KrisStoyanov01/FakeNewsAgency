package fakenewsagency.domain.models.service;

import fakenewsagency.domain.entites.Article;
import fakenewsagency.domain.entites.Comment;

import java.util.HashSet;
import java.util.Set;

public class UserServiceModel {
    private String id;
    private String username;
    private String password;
    private String email;
    private Set<Article> articles;
    private Set<Comment> comments;

    public UserServiceModel() {
        this.articles = new HashSet<>();
        this.comments = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
