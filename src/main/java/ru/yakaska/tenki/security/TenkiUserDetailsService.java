package ru.yakaska.tenki.security;

import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import ru.yakaska.tenki.entity.User;
import ru.yakaska.tenki.repository.*;

import java.util.*;

@Service
public class TenkiUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public TenkiUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Set<GrantedAuthority> authorities = Collections.emptySet();

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );

    }
}
