package ru.yakaska.tenki.service.auth;

import lombok.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import ru.yakaska.tenki.dto.*;
import ru.yakaska.tenki.entity.User;
import ru.yakaska.tenki.entity.*;
import ru.yakaska.tenki.repository.*;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserDto register(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .authorities(authorities)
                .build();
        user = userRepository.save(user);
        return new UserDto(user.getUsername());
    }

    public UserDto login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException(username);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (!authentication.isAuthenticated())
            throw new BadCredentialsException("Bad username or password");

        return new UserDto(user.get().getUsername());
    }


}
