package ru.yakaska.tenki.service;

import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import ru.yakaska.tenki.entity.User;
import ru.yakaska.tenki.repository.*;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User " + authentication.getName() + " not found")
        );
    }

}
