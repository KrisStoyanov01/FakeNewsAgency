package fakenewsagency.domain.entites;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends UserCreated{
    private String score;
    private Article articleOwner;

    public Comment() {
    }

    @Column(name = "score")
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
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
