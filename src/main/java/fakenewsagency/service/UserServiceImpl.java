package fakenewsagency.service;

import fakenewsagency.domain.entites.Group;
import fakenewsagency.domain.entites.User;
import fakenewsagency.domain.entites.Role;
import fakenewsagency.domain.models.service.UserServiceModel;
import fakenewsagency.error.UserNotFoundException;
import fakenewsagency.repository.GroupRepository;
import fakenewsagency.repository.RoleRepository;
import fakenewsagency.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public boolean register(UserServiceModel userServiceModel) {

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        this.insertUserRoles();

        if (this.userRepository.count() == 0) {
            Role user1 = this.roleRepository.findByAuthority("ROLE_USER");
            Role admin1 = this.roleRepository.findByAuthority("ROLE_ADMIN");
            Set<Role> adder = new HashSet<>();
            adder.add(user1);
            adder.add(admin1);
            user.setAuthorities(adder);
        } else {
            Role user1 = this.roleRepository.findByAuthority("ROLE_USER");
            Set<Role> adder = new HashSet<>();
            adder.add(user1);
            user.setAuthorities(adder);
        }

        this.userRepository.save(user);
        return true;
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserServiceModel> findAllUsersByGroupId(String groupId) {
        Optional<Group> group = this.groupRepository.findById(groupId);
        return this.userRepository.findUsersByGroup(group)
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel findUserByUserName(String username) {
        return this.userRepository.findByUsername(username)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public UserServiceModel findUserById(String id) {
        return this.userRepository.findById(id)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public UserServiceModel editUser(String id, UserServiceModel userServiceModel) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with the given id was not found!"));
        user.setGroup(userServiceModel.getGroup());

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }


    private void insertUserRoles() {
        if (this.roleRepository.count() == 0) {
            Role role = new Role();
            role.setAuthority("ROLE_USER");

            Role adminRole = new Role();
            adminRole.setAuthority("ROLE_ADMIN");


            this.roleRepository.save(role);
            this.roleRepository.save(adminRole);

        }
    }
}
