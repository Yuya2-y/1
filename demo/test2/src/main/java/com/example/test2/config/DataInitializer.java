package com.example.test2.config;

import com.example.test2.entity.UserAccount;
import com.example.test2.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create default admin if not present
            if (userRepository.findByUsername("admin").isEmpty()) {
                UserAccount admin = new UserAccount();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("password"));
                admin.setRoles("ROLE_ADMIN,ROLE_USER");
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("guest").isEmpty()) {
                UserAccount guest = new UserAccount();
                guest.setUsername("guest");
                guest.setPassword(passwordEncoder.encode("guest"));
                guest.setRoles("ROLE_GUEST,ROLE_USER");
                userRepository.save(guest);
            }
        };
    }
}
