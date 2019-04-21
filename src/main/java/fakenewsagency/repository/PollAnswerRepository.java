package fakenewsagency.repository;

import fakenewsagency.domain.entites.PollAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollAnswerRepository extends JpaRepository<PollAnswer, String> {
}
