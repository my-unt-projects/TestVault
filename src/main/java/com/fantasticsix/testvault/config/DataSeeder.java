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
        // Ensure roles exist before assigning them to users
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

        // Check and create the admin user
        if (userRepository.findByEmailIgnoreCase("john@testvault.com").isEmpty()) {
            User admin = new User();
            admin.setName("John Doe");
            admin.setEmail("john@testvault.com");
            admin.setPassword(passwordEncoder.encode("12345"));
            admin.setRoles(Collections.singletonList(roleAdmin));

            userRepository.save(admin);
        }

        // Check and create the normal user
        if (userRepository.findByEmailIgnoreCase("mary@testvault.com").isEmpty()) {
            User user = new User();
            user.setName("Mary Smith");
            user.setEmail("mary@testvault.com");
            user.setPassword(passwordEncoder.encode("12345"));
            user.setRoles(Collections.singletonList(roleUser));

            userRepository.save(user);
        }
    }
}
