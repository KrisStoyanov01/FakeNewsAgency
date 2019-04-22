package fakenewsagency.web.controllers;

import fakenewsagency.common.annotations.PageTitle;
import fakenewsagency.domain.entites.Group;
import fakenewsagency.domain.entites.User;
import fakenewsagency.domain.models.binding.CommentBindingModel;
import fakenewsagency.domain.models.binding.GroupBindingModel;
import fakenewsagency.domain.models.binding.UserBindingModel;
import fakenewsagency.domain.models.service.GroupServiceModel;
import fakenewsagency.domain.models.service.UserServiceModel;
import fakenewsagency.domain.models.view.GroupDetailsViewModel;
import fakenewsagency.domain.models.view.GroupListViewModel;
import fakenewsagency.service.GroupService;
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
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/groups")
public class GroupController extends BaseController {
    private final ModelMapper modelMapper;
    private final GroupService groupService;
    private final UserService userService;

    @Autowired
    public GroupController(ModelMapper modelMapper, GroupService groupService, UserService userService) {
        this.modelMapper = modelMapper;
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add Group")
    public ModelAndView add(@ModelAttribute(name = "bindingModel") GroupBindingModel groupBindingModel, ModelAndView modelAndView){
        modelAndView.addObject("groupBindingModel", groupBindingModel);
        return super.view("group/add-group", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "bindingModel") GroupBindingModel groupBindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("groupBindingModel", groupBindingModel);
            return super.view("group/add-group", modelAndView);
        }

        GroupServiceModel groupServiceModel = this.modelMapper.map(groupBindingModel, GroupServiceModel.class);
        this.groupService.addGroup(groupServiceModel);
        if (groupServiceModel == null) {
            throw new IllegalArgumentException("Group creation failed!");
        }

        return super.redirect("/groups/all");
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Show All Groups")
    public ModelAndView show(ModelAndView modelAndView,
                             @ModelAttribute(name = "viewModel") GroupListViewModel viewModel){
        modelAndView.addObject("groups",
                this.groupService.findAllGroups()
                        .stream()
                        .map(g -> this.modelMapper.map(g, GroupListViewModel.class))
                        .collect(Collectors.toList())
        );

        return super.view("group/all-groups", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PageTitle("Group Details")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsGroup(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        GroupDetailsViewModel groupDetailsViewModel = this.modelMapper.map(this.groupService.findGroupById(id), GroupDetailsViewModel.class);

        List<UserBindingModel> users = this.userService
                .findAllUsersByGroupId(id)
                .stream()
                .map(u -> this.modelMapper.map(u, UserBindingModel.class))
                .collect(Collectors.toList());


        modelAndView.addObject("group", groupDetailsViewModel);
        modelAndView.addObject("users", users);

        return super.view("group/details-group", modelAndView);
    }

    @GetMapping("/join/{id}")
    @PageTitle("Join group")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView joinGroup(@PathVariable(name = "id") String id, ModelAndView modelAndView, Principal principal) {
        GroupDetailsViewModel groupDetailsViewModel = this.modelMapper.map(this.groupService.findGroupById(id), GroupDetailsViewModel.class);

        User user = this.modelMapper.map(this.userService.findUserByUserName(principal.getName()), User.class);
        user.setGroup(this.modelMapper.map(groupDetailsViewModel, Group.class));
        this.userService.editUser(user.getId(), this.modelMapper.map(user, UserServiceModel.class));
        modelAndView.addObject("group", groupDetailsViewModel);

        return super.view("group/join-group", modelAndView);
    }
}
