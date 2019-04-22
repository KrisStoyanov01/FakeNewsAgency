package fakenewsagency.web.controllers;

import fakenewsagency.common.annotations.PageTitle;
import fakenewsagency.domain.models.binding.RequestBindingModel;
import fakenewsagency.domain.models.service.RequestServiceModel;
import fakenewsagency.domain.models.view.RequestDetailsViewModel;
import fakenewsagency.domain.models.view.RequestListViewModel;
import fakenewsagency.service.RequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/comments")
public class RequestController extends BaseController{
    private final RequestService requestService;
    private final ModelMapper modelMapper;

    @Autowired
    public RequestController(RequestService requestService, ModelMapper modelMapper) {
        this.requestService = requestService;
        this.modelMapper = modelMapper;
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
                                   BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("requestBindingModel", requestBindingModel);
            return super.view("group/add-group", modelAndView);
        }

        RequestServiceModel requestServiceModel = this.modelMapper.map(requestBindingModel, RequestServiceModel.class);
        this.requestService.addRequest(requestServiceModel);
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
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsGroup(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        RequestDetailsViewModel requestDetailsViewModel = this.modelMapper.map(this.requestService.findRequestById(id), RequestDetailsViewModel.class);

        modelAndView.addObject("request", requestDetailsViewModel);

        return super.view("request/details-request", modelAndView);
    }
}
