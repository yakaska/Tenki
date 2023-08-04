package ru.yakaska.tenki.service;

import lombok.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import ru.yakaska.tenki.repository.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
