package ru.yakaska.tenki.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.yakaska.tenki.entity.User;
import ru.yakaska.tenki.exception.TenkiException;
import ru.yakaska.tenki.dto.auth.LoginDto;
import ru.yakaska.tenki.dto.auth.RegisterDto;
import ru.yakaska.tenki.repository.UserRepository;
import ru.yakaska.tenki.security.JwtTokenProvider;
import ru.yakaska.tenki.service.AuthService;

@Service
@RequiredArgsConstructor
class AuthServiceImpl implements AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new TenkiException(HttpStatus.BAD_REQUEST, "User already registered");
        }

        User user = new User();

        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);

        return "User registered successfully.";
    }
}
