package fakenewsagency.service;

import fakenewsagency.domain.models.binding.ArticleBindingModel;
import fakenewsagency.domain.models.service.ArticleServiceModel;

import java.util.List;

public interface ArticleService {
    ArticleServiceModel addArticle(ArticleServiceModel articleServiceModel);

    ArticleServiceModel editArticle(String id, ArticleServiceModel articleServiceModel);

    List<ArticleServiceModel> findAllArticles();

    ArticleServiceModel findArticleById(String id);

    ArticleBindingModel extractArticleByIdForEditOrDelete(String id);

    boolean deleteArticle(String id);
}
