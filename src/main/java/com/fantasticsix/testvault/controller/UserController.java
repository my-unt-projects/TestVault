package com.fantasticsix.testvault.controller;

import com.fantasticsix.testvault.dto.UserDto;
import com.fantasticsix.testvault.model.Role;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.repository.RoleRepository;
import com.fantasticsix.testvault.repository.UserRepository;
import com.fantasticsix.testvault.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "edit-user";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user, @RequestParam List<Long> roles) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));

        List<Role> updatedRoles = roleRepository.findAllById(roles);
        existingUser.setRoles(updatedRoles);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setEnabled(true);
        userRepository.save(existingUser);

        return "redirect:/users"; // Redirect to the users list after update
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        user.setEnabled(false);
        userRepository.save(user);
        return "redirect:/users";
    }
}
