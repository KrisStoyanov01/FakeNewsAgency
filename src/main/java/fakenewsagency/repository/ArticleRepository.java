package fakenewsagency.repository;

import fakenewsagency.domain.entites.Article;
import fakenewsagency.domain.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {
    List<Article> findArticlesByAuthor(Optional author);
}
