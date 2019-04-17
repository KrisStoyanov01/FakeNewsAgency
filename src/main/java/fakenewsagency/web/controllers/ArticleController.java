package fakenewsagency.web.controllers;

import fakenewsagency.common.annotations.PageTitle;
import fakenewsagency.domain.entites.ArticleCategory;
import fakenewsagency.domain.entites.Comment;
import fakenewsagency.domain.entites.User;
import fakenewsagency.domain.models.binding.ArticleBindingModel;
import fakenewsagency.domain.models.service.ArticleServiceModel;
import fakenewsagency.domain.models.view.ArticleDetailsViewModel;
import fakenewsagency.domain.models.view.ArticleListViewModel;
import fakenewsagency.service.ArticleService;
import fakenewsagency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/articles")
public class ArticleController extends BaseController{

    private final ModelMapper modelMapper;
    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public ArticleController(ModelMapper modelMapper, ArticleService articleService, UserService userService) {
        this.modelMapper = modelMapper;
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add Article")
    public ModelAndView add(@ModelAttribute(name = "bindingModel") ArticleBindingModel articleBindingModel, ModelAndView modelAndView){
        modelAndView.addObject("articleBindingModel", articleBindingModel);
        modelAndView.addObject("categories", ArticleCategory.values());
        return super.view("article/add-article", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "bindingModel") ArticleBindingModel articleBindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView, Principal principal) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("articleBindingModel", articleBindingModel);
            modelAndView.addObject("categories", ArticleCategory.values());
            return super.view("article/add-article", modelAndView);
        }

        User author = this.modelMapper.map(this.userService.findUserByUserName(principal.getName()), User.class);
        articleBindingModel.setAuthor(author);
        articleBindingModel.setViews(0);
        articleBindingModel.setComments(new HashSet<>());
        ArticleServiceModel articleServiceModel = this.modelMapper.map(articleBindingModel, ArticleServiceModel.class);
        this.articleService.addArticle(articleServiceModel);
        if (articleServiceModel == null) {
            throw new IllegalArgumentException("Article creation failed!");
        }

        return super.redirect("/articles/show");
    }

    @GetMapping("/show")
    @PageTitle("Show All Articles")
    public ModelAndView show(ModelAndView modelAndView,
                             @ModelAttribute(name = "viewModel") ArticleListViewModel viewModel){
        modelAndView.addObject("articles",
                this.articleService.findAllArticles()
                        .stream()
                        .map(a -> this.modelMapper.map(a, ArticleListViewModel.class))
                        .collect(Collectors.toList())
        );


        return super.view("article/all-articles", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PageTitle("Article Details")
    public ModelAndView detailsProduct(@PathVariable String id, ModelAndView modelAndView) {
        ArticleDetailsViewModel articleDetailsViewModel = this.modelMapper.map(this.articleService.findArticleById(id), ArticleDetailsViewModel.class);
        articleDetailsViewModel.setViews(articleDetailsViewModel.getViews() + 1);
        this.articleService.editArticle(articleDetailsViewModel.getId(), this.modelMapper.map(articleDetailsViewModel, ArticleServiceModel.class));
        modelAndView.addObject("article", articleDetailsViewModel);
        return super.view("article/details-article", modelAndView);
    }
}
