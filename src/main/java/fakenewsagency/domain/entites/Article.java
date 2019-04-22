package fakenewsagency.domain.entites;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
public class Article extends UserCreated {

    private String title;
    private ArticleCategory articleCategory;
    private Integer views;
    private Set<Comment> comments;

    public Article() {
        this.comments = new HashSet<>();
    }

    @Column(name = "title", unique = true, nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "article_category", nullable = false)
    public ArticleCategory getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(ArticleCategory articleCategory) {
        this.articleCategory = articleCategory;
    }

    @Column(name = "views", columnDefinition = "int default 0")
    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}
