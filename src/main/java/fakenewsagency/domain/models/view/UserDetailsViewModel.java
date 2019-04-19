package fakenewsagency.domain.models.view;

import fakenewsagency.domain.entites.Article;
import fakenewsagency.domain.entites.Comment;
import fakenewsagency.domain.entites.Role;

import java.util.Set;

public class UserDetailsViewModel {
    private String username;
    private String password;
    private String email;
    private Set<Article> articles;
    private Set<Comment> comments;
    private Set<Role> authorities;

    public UserDetailsViewModel() {
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

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }
}
