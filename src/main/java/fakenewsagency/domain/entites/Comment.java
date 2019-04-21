package fakenewsagency.domain.entites;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends UserCreated{
    private Integer score;
    private Article articleOwner;

    public Comment() {
    }

    @Column(name = "score", nullable = false)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    public Article getArticleOwner() {
        return articleOwner;
    }

    public void setArticleOwner(Article articleOwner) {
        this.articleOwner = articleOwner;
    }
}
