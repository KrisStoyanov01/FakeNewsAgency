package fakenewsagency.domain.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment extends UserCreated{
    private String score;

    public Comment() {
    }

    @Column(name = "score")
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
