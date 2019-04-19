package fakenewsagency.web.controllers;

import fakenewsagency.common.annotations.PageTitle;
import fakenewsagency.domain.models.binding.UserRegisterBindingModel;
import fakenewsagency.domain.models.service.UserServiceModel;
import fakenewsagency.domain.models.view.UserListViewModel;
import fakenewsagency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController extends BaseController{

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
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
                    //user.setAuthorities(u.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet()));

                    return user;
                })
                .collect(Collectors.toList());
        
        modelAndView.addObject("users", users);

        return super.view("user/all-users", modelAndView);
    }

}
