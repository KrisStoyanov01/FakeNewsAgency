package fakenewsagency.web.controllers;

import fakenewsagency.common.annotations.PageTitle;
import fakenewsagency.domain.entites.User;
import fakenewsagency.domain.models.binding.RequestBindingModel;
import fakenewsagency.domain.models.service.RequestServiceModel;
import fakenewsagency.domain.models.service.UserServiceModel;
import fakenewsagency.domain.models.view.RequestDetailsViewModel;
import fakenewsagency.domain.models.view.RequestListViewModel;
import fakenewsagency.service.RequestService;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/requests")
public class RequestController extends BaseController{
    private final RequestService requestService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public RequestController(RequestService requestService, ModelMapper modelMapper, UserService userService) {
        this.requestService = requestService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add Request")
    public ModelAndView add(@ModelAttribute(name = "bindingModel") RequestBindingModel requestBindingModel, ModelAndView modelAndView){
        modelAndView.addObject("requestBindingModel", requestBindingModel);
        return super.view("request/add-request", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "bindingModel") RequestBindingModel requestBindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView, Principal principal) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("requestBindingModel", requestBindingModel);
            return super.view("request/add-request", modelAndView);
        }
        User user = this.modelMapper.map(this.userService.findUserByUserName(principal.getName()), User.class);
        RequestServiceModel requestServiceModel = this.modelMapper.map(requestBindingModel, RequestServiceModel.class);
        this.requestService.addRequest(requestServiceModel, this.modelMapper.map(user, UserServiceModel.class));
        if (requestServiceModel == null) {
            throw new IllegalArgumentException("Request creation failed!");
        }

        return super.redirect("/home");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Show All Requests")
    public ModelAndView show(ModelAndView modelAndView,
                             @ModelAttribute(name = "viewModel") RequestListViewModel viewModel){
        modelAndView.addObject("requests",
                this.requestService.findAllRequests()
                        .stream()
                        .map(r -> this.modelMapper.map(r, RequestListViewModel.class))
                        .collect(Collectors.toList())
        );

        return super.view("request/all-requests", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PageTitle("Group Details")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView detailsRequest(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        RequestDetailsViewModel requestDetailsViewModel = this.modelMapper.map(this.requestService.findRequestById(id), RequestDetailsViewModel.class);

        modelAndView.addObject("request", requestDetailsViewModel);

        return super.view("request/details-request", modelAndView);
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Delete Request")
    public ModelAndView deleteRequest(@PathVariable String id, ModelAndView modelAndView) {
        RequestServiceModel requestServiceModel = this.requestService.findRequestById(id);
        RequestBindingModel requestBindingModel = this.modelMapper.map(requestServiceModel, RequestBindingModel.class);

        modelAndView.addObject("request", requestBindingModel);
        modelAndView.addObject("requestId", id);

        return super.view("request/delete-request", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteRequestConfirm(@PathVariable String id) {
        this.requestService.deleteRequest(id);

        return super.redirect("/home");
    }
}
