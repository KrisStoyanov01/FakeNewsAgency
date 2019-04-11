package fakenewsagency.web.controllers;

import fakenewsagency.domain.models.binding.UserRegisterBindingModel;
import fakenewsagency.domain.models.service.UserServiceModel;
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
    public ModelAndView register(ModelAndView modelAndView){

        return super.view("register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel model,
                                        ModelAndView modelAndView){
        if(!model.getPassword().equals(model.getConfirmPassword())){
            throw new IllegalArgumentException(("Passwords don't match!"));
        }

        this.userService.register(this.modelMapper.map(model, UserServiceModel.class));

        return super.redirect("/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(ModelAndView modelAndView){

        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession session){

        session.invalidate();

        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }


}
