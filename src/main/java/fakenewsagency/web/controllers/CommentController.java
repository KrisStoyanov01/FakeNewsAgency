package fakenewsagency.web.controllers;

import fakenewsagency.common.annotations.PageTitle;
import fakenewsagency.domain.entites.Article;
import fakenewsagency.domain.entites.Comment;
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

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("commentBindingModel", commentBindingModel);
            ArticleDetailsViewModel articleDetailsViewModel = this.modelMapper.map(this.articleService.findArticleById(articleId), ArticleDetailsViewModel.class);
            modelAndView.addObject("article", articleDetailsViewModel);
            return super.view("comment/add-comment", modelAndView);
        }

        UserServiceModel userServiceModel = this.userService.findUserByUserName(principal.getName());
        commentBindingModel.setAuthor(this.modelMapper.map(userServiceModel, User.class));
        commentBindingModel.setScore(0);
        commentBindingModel.setArticleOwner(this.modelMapper.map(this.articleService.findArticleById(articleId), Article.class));
        CommentServiceModel commentServiceModel = this.modelMapper.map(commentBindingModel, CommentServiceModel.class);
        this.commentService.addComment(commentServiceModel);
        if (commentServiceModel == null) {
            throw new IllegalArgumentException("Comment creation failed!");
        }

        return super.redirect("/articles/details/" + articleId);
    }

    @GetMapping(value = "/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Edit Comment")
    public ModelAndView editComment(@PathVariable(name = "id") String id, ModelAndView modelAndView) {

        CommentBindingModel commentBindingModel = this.commentService.extractCommentByIdForEditOrDelete(id);
        modelAndView.addObject("comment", commentBindingModel);
        modelAndView.addObject("commentId", id);
        return super.view("comment/edit-comment", modelAndView);
    }


    @PostMapping("/edit/{id}")
    public ModelAndView editCommentConfirm(@PathVariable(name = "id") String id, @ModelAttribute CommentBindingModel commentBindingModel) {
        this.commentService.editComment(id, this.modelMapper.map(commentBindingModel, CommentServiceModel.class));
        String articleId = this.commentService.findCommentById(id).getArticleOwner().getId();

        return super.redirect("/articles/details/" + articleId);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Delete Article")
    public ModelAndView deleteComment(@PathVariable String id, ModelAndView modelAndView) {
        CommentServiceModel commentServiceModel= this.commentService.findCommentById(id);
        CommentBindingModel commentBindingModel = this.modelMapper.map(commentServiceModel, CommentBindingModel.class);

        //model.setCategories(productServiceModel.getCategories().stream().map(c -> c.getName()).collect(Collectors.toList()));

        modelAndView.addObject("comment", commentBindingModel);
        modelAndView.addObject("commentId", id);

        return super.view("comment/delete-comment", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteCommentConfirm(@PathVariable String id) {
        CommentServiceModel readyModel = this.commentService.findCommentById(id);
        String articleId = readyModel.getArticleOwner().getId();
        this.commentService.deleteComment(id);
        return super.redirect("/articles/details/" + articleId);
    }
}
