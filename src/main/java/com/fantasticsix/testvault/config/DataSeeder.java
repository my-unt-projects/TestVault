package com.fantasticsix.testvault.config;

import com.fantasticsix.testvault.model.Role;
import com.fantasticsix.testvault.model.User;
import com.fantasticsix.testvault.repository.RoleRepository;
import com.fantasticsix.testvault.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedDatabase(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return new CommandLineRunner() {
            @Override
            @Transactional
            public void run(String... args) throws Exception {
                DataSeeder.this.seedData(userRepository, roleRepository, passwordEncoder);
            }
        };
    }

    public void seedData(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        if (roleAdmin == null) {
            roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleAdmin = roleRepository.save(roleAdmin);
        }

        Role roleUser = roleRepository.findByName("ROLE_USER");
        if (roleUser == null) {
            roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleUser = roleRepository.save(roleUser);
        }

        Role roleManager = roleRepository.findByName("ROLE_MANAGER");
        if (roleManager == null) {
            roleManager = new Role();
            roleManager.setName("ROLE_MANAGER");
            roleManager = roleRepository.save(roleManager);
        }

        Role roleDev = roleRepository.findByName("ROLE_DEV");
        if (roleDev == null) {
            roleDev = new Role();
            roleDev.setName("ROLE_DEV");
            roleDev = roleRepository.save(roleDev);
        }

        Role roleQa = roleRepository.findByName("ROLE_QA");
        if (roleQa == null) {
            roleQa = new Role();
            roleQa.setName("ROLE_QA");
            roleQa = roleRepository.save(roleQa);
        }

        // Create Admin User
        if (userRepository.findByEmailIgnoreCase("admin@testvault.com").isEmpty()) {
            User admin = new User();
            admin.setName("John Doe");
            admin.setEmail("admin@testvault.com");
            admin.setEnabled(true);
            admin.setPassword(passwordEncoder.encode("12345"));
            admin.setRoles(Collections.singletonList(roleAdmin));

            userRepository.save(admin);
        }

        // Create Regular User
        if (userRepository.findByEmailIgnoreCase("user@testvault.com").isEmpty()) {
            User user = new User();
            user.setName("Jane Doe");
            user.setEmail("user@testvault.com");
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode("12345"));
            user.setRoles(Collections.singletonList(roleUser));

            userRepository.save(user);
        }

        // Create Manager User
        if (userRepository.findByEmailIgnoreCase("manager@testvault.com").isEmpty()) {
            User manager = new User();
            manager.setName("Mary Smith");
            manager.setEmail("manager@testvault.com");
            manager.setEnabled(true);
            manager.setPassword(passwordEncoder.encode("12345"));
            manager.setRoles(Collections.singletonList(roleManager));

            userRepository.save(manager);
        }

        // Create Developer User
        if (userRepository.findByEmailIgnoreCase("dev@testvault.com").isEmpty()) {
            User dev = new User();
            dev.setName("James Brown");
            dev.setEmail("dev@testvault.com");
            dev.setEnabled(true);
            dev.setPassword(passwordEncoder.encode("12345"));
            dev.setRoles(Collections.singletonList(roleDev));

            userRepository.save(dev);
        }

        // Create QA User
        if (userRepository.findByEmailIgnoreCase("qa@testvault.com").isEmpty()) {
            User qa = new User();
            qa.setName("Janice Green");
            qa.setEmail("qa@testvault.com");
            qa.setEnabled(true);
            qa.setPassword(passwordEncoder.encode("12345"));
            qa.setRoles(Collections.singletonList(roleQa));

            userRepository.save(qa);
        }

    }
}
