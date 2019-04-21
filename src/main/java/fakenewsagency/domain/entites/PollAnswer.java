package fakenewsagency.domain.entites;

import javax.persistence.*;

@Entity
@Table(name = "poll_answers")
public class PollAnswer extends BaseEntity{
    private String message;
    private Integer timesChosen;
    private Poll poll;

    public PollAnswer() {
    }

    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name = "times_chosen", unique = true, nullable = false)
    public Integer getTimesChosen() {
        return timesChosen;
    }

    public void setTimesChosen(Integer timesChosen) {
        this.timesChosen = timesChosen;
    }

    @ManyToOne
    @JoinColumn(name = "poll_id", referencedColumnName = "id")
    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
