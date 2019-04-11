package fakenewsagency.service;

import fakenewsagency.domain.models.binding.ArticleBindingModel;
import fakenewsagency.domain.models.service.ArticleServiceModel;

import java.util.List;

public interface ArticleService {
    ArticleServiceModel addArticle(ArticleServiceModel articleServiceModel);

    void editArticle(ArticleBindingModel articleBindingModel);

    List<ArticleServiceModel> findAllArticles();

    ArticleServiceModel findArticlesById(String id);

    ArticleBindingModel extractArticleByIdForEditOrDelete(String id);

    boolean deleteArticle(String id);
}
