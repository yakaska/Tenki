package ru.yakaska.tenki;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.*;
import ru.yakaska.tenki.entity.Role;
import ru.yakaska.tenki.entity.*;
import ru.yakaska.tenki.repository.*;

import java.util.*;

@SpringBootApplication
public class TenkiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenkiApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role role = new Role();
            role.setAuthority("USER");
            Role userRole = roleRepository.save(role);

            Set<Role> roles = new HashSet<>();
            roles.add(userRole);

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("password"));
            user.setAuthorities(roles);

            userRepository.save(user);
        };


    }

}
