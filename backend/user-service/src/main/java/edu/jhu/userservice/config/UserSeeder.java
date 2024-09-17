package edu.jhu.userservice.config;


import edu.jhu.userservice.entity.User;
import edu.jhu.userservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class UserSeeder {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @PostConstruct
    public void seedUsers() {
        System.out.println("Seeding users");
        if (userRepository.count() >= 0) {
            User user1 = new User("Alice Smith", "alice@example.com", passwordEncoder.encode("password1"));
            User user2 = new User("Bob Johnson", "bob@example.com", passwordEncoder.encode("password2"));
            User user3 = new User("Charlie Brown", "charlie@example.com", passwordEncoder.encode("password3"));
            User user4 = new User("David Lee", "david@example.com", passwordEncoder.encode("password4"));
            User user5 = new User("Eva Green", "eva@example.com", passwordEncoder.encode("password5"));

            userRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5));
            System.out.println("Users seeded");
        } else {
            System.out.println("Users already exist, skipping seeding.");
        }
    }
}
