package fakenewsagency.service;

import fakenewsagency.domain.entites.Article;
import fakenewsagency.domain.models.binding.ArticleBindingModel;
import fakenewsagency.domain.models.service.ArticleServiceModel;
import fakenewsagency.error.ArticleNotFoundException;
import fakenewsagency.repository.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        try{
            this.articleRepository.saveAndFlush(article);
            return this.modelMapper.map(article, ArticleServiceModel.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArticleServiceModel editArticle(String id, ArticleServiceModel articleServiceModel) {
        Article article = this.articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Product with the given id was not found!"));

        /*
        articleServiceModel.setCategories(
                this.categoryService.findAllCategories()
                        .stream()
                        .filter(c -> productServiceModel.getCategories().contains(c.getId()))
                        .collect(Collectors.toList())
        );*/

        article.setAuthor(articleServiceModel.getAuthor());
        article.setContent(articleServiceModel.getContent());
        article.setCreatedOn(articleServiceModel.getCreatedOn());
        article.setTitle(articleServiceModel.getTitle());
        article.setArticleCategory(articleServiceModel.getArticleCategory());
        article.setViews(articleServiceModel.getViews());
        article.setComments(articleServiceModel.getComments());

        return this.modelMapper.map(this.articleRepository.saveAndFlush(article), ArticleServiceModel.class);
    }

    @Override
    public List<ArticleServiceModel> findAllArticles() {
        return this.articleRepository.findAll()
                .stream()
                .map(a -> this.modelMapper.map(a, ArticleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ArticleServiceModel findArticleById(String id) {
        return this.articleRepository.findById(id)
                .map(p -> {
                    ArticleServiceModel productServiceModel = this.modelMapper.map(p, ArticleServiceModel.class);
                    this.articleRepository.findById(productServiceModel.getId());

                    return productServiceModel;
                })
                .orElseThrow(() -> new ArticleNotFoundException("Product with the given id was not found!"));
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
