package ru.yakaska.tenki.controller;

import lombok.*;
import org.springframework.web.bind.annotation.*;
import ru.yakaska.tenki.dto.*;
import ru.yakaska.tenki.service.auth.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
// TODO: 04.08.2023 add validation and exception handling
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody RegisterDto registerDto) {
        return authService.register(registerDto.getUsername(), registerDto.getPassword());
    }

    @PostMapping("/login")
    public UserDto loginUser(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto.getUsername(), loginDto.getPassword());
    }

}
