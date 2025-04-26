package com.fantasticsix.testvault.service.impl;

import com.fantasticsix.testvault.dto.UserDto;
import com.fantasticsix.testvault.model.Role;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.repository.RoleRepository;
import com.fantasticsix.testvault.repository.UserRepository;
import com.fantasticsix.testvault.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) { //no need for one constructor to have @Autowired annotation.
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());  //conversion of form data to jpa entity, here mapper won't work as userDto don't have common attributes with User.
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // before setting the password we are encrypting using Bcrypt by Spring security.

        //userRepository.save(user);

        Role role = roleRepository.findByName("ROLE_USER");

        if (role == null) {
            role = checkRoleExists();
        }
        user.setRoles(Arrays.asList(role));   //As we have list of roles field in user checking any role in db exists or not if not creating by a private function and saving it in db
        userRepository.save(user); // now saving user to db.

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> user_dto = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEnabled(user.isEnabled());
            userDto.setEmail(user.getEmail());
            user_dto.add(userDto);
        }
        return user_dto;
    }

    private Role checkRoleExists() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
