package fakenewsagency.repository;

import fakenewsagency.domain.entites.Comment;
import fakenewsagency.domain.entites.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
}
