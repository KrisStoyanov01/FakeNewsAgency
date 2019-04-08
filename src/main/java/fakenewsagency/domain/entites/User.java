package fakenewsagency.domain.entites;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    private String username;
    private String password;
    private String email;
    private Set<Article> articles;
    private Set<Comment> comments;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private Set<UserRole> authorities;


    public User(){
        this.authorities = new HashSet<>();
    }

    @Override
    @Column(name = "username", nullable = false, unique = true, updatable = false)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    @Column(name = "is_account_non_expired")
    public boolean isAccountNonExpired() {
        return true;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    @Override
    @Column(name = "is_account_non_locked")
    public boolean isAccountNonLocked() {
        return true;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    @Override
    @Column(name = "is_credentials_non_expired")
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    @Override
    @Column(name = "is_enabled")
    public boolean isEnabled() {
        return true;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    @ManyToMany(targetEntity = UserRole.class, fetch = FetchType.EAGER)
    public Set<UserRole> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<UserRole> authorities) {
        this.authorities = authorities;
    }

    @Column(name = "articles")
    @OneToMany(mappedBy = "author")
    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    @Column(name = "comments")
    @OneToMany(mappedBy = "author")
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
