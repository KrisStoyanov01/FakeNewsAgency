package fakenewsagency.service;

import fakenewsagency.domain.entites.User;
import fakenewsagency.domain.entites.UserRole;
import fakenewsagency.domain.models.service.UserServiceModel;
import fakenewsagency.repository.RoleRepository;
import fakenewsagency.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean register(UserServiceModel userServiceModel) {

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        this.insertUserRoles();

        if (this.userRepository.count() == 0) {
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_ADMIN"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
        } else {
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROLE_USER"));
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
    public UserServiceModel findUserByUserName(String username) {
        return this.userRepository.findByUsername(username)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }


    private void insertUserRoles() {
        if (this.roleRepository.count() == 0) {
            UserRole userRole = new UserRole();
            userRole.setAuthority("ROLE_USER");

            UserRole adminRole = new UserRole();
            adminRole.setAuthority("ROLE_ADMIN");


            this.roleRepository.save(userRole);
            this.roleRepository.save(adminRole);

        }
    }
}
