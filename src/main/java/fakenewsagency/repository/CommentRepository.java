package fakenewsagency.repository;

import fakenewsagency.domain.entites.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    public List<Comment> findCommentsByArticleOwner(Optional articleOwner);
}
