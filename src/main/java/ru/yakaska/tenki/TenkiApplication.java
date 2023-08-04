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
    CommandLineRunner run(LocationRepository locationRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role role = new Role();
            role.setAuthority("USER");
            Role userRole = roleRepository.save(role);
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);

            Location location = new Location();
            location.setName("Moscow");
            location.setLatitude(37.6156);
            location.setLongitude(55.7522);
            Location userLocation = locationRepository.save(location);
            Set<Location> locations = new HashSet<>();
            locations.add(userLocation);

            location = new Location();
            location.setName("Saint Petersburg");
            location.setLatitude(30.2642);
            location.setLongitude(59.8944);
            userLocation = locationRepository.save(location);
            locations.add(userLocation);

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("password"));
            user.setAuthorities(roles);
            user.setLocations(locations);

            userRepository.save(user);
        };


    }

}
