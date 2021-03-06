package fakenewsagency.service;

import fakenewsagency.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean register(UserServiceModel userServiceModel);

    List<UserServiceModel> findAllUsers();

    List<UserServiceModel> findAllUsersByGroupId(String groupId);

    UserServiceModel findUserByUserName(String username);

    UserServiceModel findUserById(String id);

    UserServiceModel editUser(String id, UserServiceModel userServiceModel);
}
