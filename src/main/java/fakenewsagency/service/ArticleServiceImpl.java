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
                .orElseThrow(() -> new ArticleNotFoundException("Article with the given id was not found!"));

        /*
        articleServiceModel.setCategories(
                this.categoryService.findAllCategories()
                        .stream()
                        .filter(c -> productServiceModel.getCategories().contains(c.getId()))
                       .collect(Collectors.toList())
        );*/
        article.setViews(articleServiceModel.getViews());
        article.setContent(articleServiceModel.getContent());
        article.setTitle(articleServiceModel.getTitle());
        article.setArticleCategory(articleServiceModel.getArticleCategory());
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
                    ArticleServiceModel articleServiceModel = this.modelMapper.map(p, ArticleServiceModel.class);
                    this.articleRepository.findById(articleServiceModel.getId());

                    return articleServiceModel;
                })
                .orElseThrow(() -> new ArticleNotFoundException("Article with the given id was not found!"));
    }

    @Override
    public ArticleBindingModel extractArticleByIdForEditOrDelete(String id) {
        Article article = this.articleRepository.findById(id).orElse(null);

        if (article == null) {
            throw new IllegalArgumentException("Invalid id");
        }

        ArticleBindingModel articleBindingModel = this.modelMapper.map(article, ArticleBindingModel.class);
        /*
        List<String> capitalIds =
                virus.getCapitals().stream().map(Capital::getId).collect(Collectors.toList());

        virusBindingModel.setCapitals(capitalIds);
        */
        return articleBindingModel;
    }

    @Override
    public boolean  deleteArticle(String id) {
        try{
            this.articleRepository.deleteById(id);

            return true;
        }catch (Exception e){
            e.printStackTrace();

            return false;
        }
    }
}
