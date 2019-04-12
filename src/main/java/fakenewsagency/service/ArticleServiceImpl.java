package fakenewsagency.service;

import fakenewsagency.domain.entites.Article;
import fakenewsagency.domain.models.binding.ArticleBindingModel;
import fakenewsagency.domain.models.service.ArticleServiceModel;
import fakenewsagency.repository.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ModelMapper modelMapper;
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ModelMapper modelMapper, ArticleRepository articleRepository) {
        this.modelMapper = modelMapper;
        this.articleRepository = articleRepository;
    }

    @Override
    public ArticleServiceModel addArticle(ArticleServiceModel articleServiceModel) {
        Article article = this.modelMapper.map(articleServiceModel, Article.class);
        article.setViews(0);
        try{
            this.articleRepository.saveAndFlush(article);

            return this.modelMapper.map(article, ArticleServiceModel.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void editArticle(ArticleBindingModel articleBindingModel) {

    }

    @Override
    public List<ArticleServiceModel> findAllArticles() {
        return null;
    }

    @Override
    public ArticleServiceModel findArticlesById(String id) {
        return null;
    }

    @Override
    public ArticleBindingModel extractArticleByIdForEditOrDelete(String id) {
        return null;
    }

    @Override
    public boolean deleteArticle(String id) {
        return false;
    }
}
