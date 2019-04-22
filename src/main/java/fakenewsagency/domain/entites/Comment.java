package fakenewsagency.domain.entites;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends UserCreated{
    private Article articleOwner;

    public Comment() {
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
