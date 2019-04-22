package fakenewsagency.repository;

import fakenewsagency.domain.entites.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, String> {
    List<Request> findRequestsByAuthor(Optional author);
}
