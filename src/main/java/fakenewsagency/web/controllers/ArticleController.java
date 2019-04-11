package fakenewsagency.web.controllers;

import fakenewsagency.common.annotations.PageTitle;
import fakenewsagency.domain.entites.ArticleCategory;
import fakenewsagency.domain.models.binding.ArticleBindingModel;
import fakenewsagency.domain.models.service.ArticleServiceModel;
import fakenewsagency.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/articles")
public class ArticleController extends BaseController{

    private final ModelMapper modelMapper;
    private final ArticleService articleService;

    public ArticleController(ModelMapper modelMapper, ArticleService articleService) {
        this.modelMapper = modelMapper;
        this.articleService = articleService;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add Article")
    public ModelAndView add(@ModelAttribute(name = "bindingModel") ArticleBindingModel bindingModel, ModelAndView modelAndView){
        modelAndView.addObject("articleBindingModel", bindingModel);
        modelAndView.addObject("categories", ArticleCategory.values());
        return super.view("article/add-article", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "bindingModel") ArticleBindingModel bindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("articleBindingModel", bindingModel);

            return super.view("article/add-article", modelAndView);
        }


        ArticleServiceModel articleServiceModel = this.modelMapper.map(bindingModel, ArticleServiceModel.class);
        this.articleService.addArticle(articleServiceModel);

        if (articleServiceModel == null) {
            throw new IllegalArgumentException("Article creation failed!");
        }

        //return super.redirect("/articles/show");
        return super.redirect("/home");
    }
}
