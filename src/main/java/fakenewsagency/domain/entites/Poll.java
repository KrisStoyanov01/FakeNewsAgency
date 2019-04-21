package fakenewsagency.domain.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "polls")
public class Poll extends UserCreated {
    private Set<String> possibleAnswers;

    public Poll() {
        this.possibleAnswers = new HashSet<>();
    }

    @Column(name = "possible_answers")
    @OneToMany(mappedBy = "poll")
    public Set<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(Set<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }
}
