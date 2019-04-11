package fakenewsagency.web.controllers;


import fakenewsagency.common.annotations.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


@Controller
public class HomeController extends BaseController{

    public HomeController() {
    }

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Index")
    public ModelAndView index(){
        return super.view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Home")
    public ModelAndView home(Principal principal, ModelAndView modelAndView) {

        modelAndView.addObject("username", principal.getName());

        return super.view("home", modelAndView);
    }

}
