package ru.yakaska.tenki.service;

import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import ru.yakaska.tenki.entity.*;
import ru.yakaska.tenki.repository.*;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
// TODO: 04.08.2023 Add DTO's

public class AuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthenticationManager authenticationManager;

    public User register(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setAuthorities(authorities);
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return userRepository.findByUsername(username).get();
    }


}
