package fakenewsagency.web.controllers;

import fakenewsagency.common.annotations.PageTitle;
import fakenewsagency.domain.entites.Article;
import fakenewsagency.domain.entites.Comment;
import fakenewsagency.domain.models.binding.UserRegisterBindingModel;
import fakenewsagency.domain.models.service.UserServiceModel;
import fakenewsagency.domain.models.view.ArticleListViewModel;
import fakenewsagency.domain.models.view.CommentListViewModel;
import fakenewsagency.domain.models.view.UserDetailsViewModel;
import fakenewsagency.domain.models.view.UserListViewModel;
import fakenewsagency.service.ArticleService;
import fakenewsagency.service.CommentService;
import fakenewsagency.service.GroupService;
import fakenewsagency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController extends BaseController{

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CommentService commentService;
    private final ArticleService articleService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, CommentService commentService, ArticleService articleService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.commentService = commentService;
        this.articleService = articleService;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Register")
    public ModelAndView register() {
        return super.view("register");
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel model) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            return super.view("register");
        }

        this.userService.register(this.modelMapper.map(model, UserServiceModel.class));

        return super.redirect("/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Login")
    public ModelAndView login() {
        return super.view("login");
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession session){

        session.invalidate();

        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }

    @GetMapping("/users/all")
    @PageTitle("All Users")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView allUsers(ModelAndView modelAndView) {
        List<UserListViewModel> users = this.userService.findAllUsers()
                .stream()
                .map(u -> {
                    UserListViewModel user = this.modelMapper.map(u, UserListViewModel.class);
                    return user;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("users", users);

        return super.view("user/all-users", modelAndView);
    }

    @GetMapping("/users/details/{id}")
    @PageTitle("Article Details")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsUser(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(this.userService.findUserById(id), UserDetailsViewModel.class);
        modelAndView.addObject("user", userDetailsViewModel);
        List<ArticleListViewModel> articles = this.articleService.findAllArticlesByUserId(id).stream().map(a -> this.modelMapper.map(a, ArticleListViewModel.class)).collect(Collectors.toList());
        List<CommentListViewModel> comments = this.commentService.findAllCommentsByUserId(id).stream().map(c -> this.modelMapper.map(c, CommentListViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("articles", articles);
        modelAndView.addObject("comments", comments);
        return super.view("user/details-user", modelAndView);
    }
}
