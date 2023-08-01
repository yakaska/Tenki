package ru.yakaska.tenki.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import ru.yakaska.tenki.entity.User;
import ru.yakaska.tenki.repository.*;
import ru.yakaska.tenki.security.*;

import java.util.*;

@Service
public class TenkiUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public TenkiUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new TenkiUserDetails(user.get());
    }
}
