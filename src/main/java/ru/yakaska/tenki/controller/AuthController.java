package ru.yakaska.tenki.controller;

import lombok.*;
import org.springframework.web.bind.annotation.*;
import ru.yakaska.tenki.dto.*;
import ru.yakaska.tenki.entity.*;
import ru.yakaska.tenki.service.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public User registerUser(@RequestBody UserDto user) {
        return authService.register(user.getUsername(), user.getPassword());
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody UserDto user) {
        System.out.println(user.getUsername());
        return authService.login(user.getUsername(), user.getPassword());
    }

}
