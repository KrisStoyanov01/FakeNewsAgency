package fakenewsagency.web.controllers;

import fakenewsagency.common.annotations.PageTitle;
import fakenewsagency.domain.entites.Article;
import fakenewsagency.domain.entites.User;
import fakenewsagency.domain.models.binding.CommentBindingModel;
import fakenewsagency.domain.models.service.CommentServiceModel;
import fakenewsagency.domain.models.service.UserServiceModel;
import fakenewsagency.domain.models.view.ArticleDetailsViewModel;
import fakenewsagency.service.ArticleService;
import fakenewsagency.service.CommentService;
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

@Controller
@RequestMapping("/comments")
public class CommentController extends BaseController {
    private final ModelMapper modelMapper;
    private final CommentService commentService;
    private final UserService userService;
    private final ArticleService articleService;

    @Autowired
    public CommentController(ModelMapper modelMapper, CommentService commentService, UserService userService, ArticleService articleService) {
        this.modelMapper = modelMapper;
        this.commentService = commentService;
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping("/add/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add Article")
    public ModelAndView add(@PathVariable(name = "id") String articleId, @ModelAttribute(name = "bindingModel") CommentBindingModel commentBindingModel, ModelAndView modelAndView){
        modelAndView.addObject("commentBindingModel", commentBindingModel);
        ArticleDetailsViewModel articleDetailsViewModel = this.modelMapper.map(this.articleService.findArticleById(articleId), ArticleDetailsViewModel.class);
        modelAndView.addObject("article", articleDetailsViewModel);
        return super.view("comment/add-comment", modelAndView);
    }

    @PostMapping("/add/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addConfirm(@PathVariable(name = "id") String articleId, @Valid @ModelAttribute(name = "bindingModel") CommentBindingModel commentBindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView, Principal principal) {

        /*if (bindingResult.hasErrors()) {
            //todo add error handling
            modelAndView.addObject("commentBindingModel", commentBindingModel);
            ArticleDetailsViewModel articleDetailsViewModel = this.modelMapper.map(this.articleService.findArticleById(articleId), ArticleDetailsViewModel.class);
            modelAndView.addObject("article", articleDetailsViewModel);
            return super.view("comment/add-comment", modelAndView);
        }*/

        UserServiceModel userServiceModel = this.userService.findUserByUserName(principal.getName());
        commentBindingModel.setAuthor(this.modelMapper.map(userServiceModel, User.class));
        commentBindingModel.setScore(0);
        commentBindingModel.setArticleOwner(this.modelMapper.map(this.articleService.findArticleById(articleId), Article.class));
        CommentServiceModel commentServiceModel = this.modelMapper.map(commentBindingModel, CommentServiceModel.class);
        //todo add article owner and fix date
        this.commentService.addComment(commentServiceModel);
        if (commentServiceModel == null) {
            throw new IllegalArgumentException("Comment creation failed!");
        }

        return super.redirect("/articles/show");
        //return super.redirect("/home");
    }
}
