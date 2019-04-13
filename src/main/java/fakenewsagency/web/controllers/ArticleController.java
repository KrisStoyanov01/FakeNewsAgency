package fakenewsagency.web.controllers;

import fakenewsagency.common.annotations.PageTitle;
import fakenewsagency.domain.entites.ArticleCategory;
import fakenewsagency.domain.entites.User;
import fakenewsagency.domain.models.binding.ArticleBindingModel;
import fakenewsagency.domain.models.service.ArticleServiceModel;
import fakenewsagency.service.ArticleService;
import fakenewsagency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

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
        ArticleServiceModel articleServiceModel = this.modelMapper.map(articleBindingModel, ArticleServiceModel.class);
        this.articleService.addArticle(articleServiceModel);
        if (articleServiceModel == null) {
            throw new IllegalArgumentException("Article creation failed!");
        }

        //return super.redirect("/articles/show");
        return super.redirect("/home");
    }
}
