package ru.yakaska.tenki.controller;

import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import ru.yakaska.tenki.dto.*;
import ru.yakaska.tenki.service.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegisterService registerService;

    public AuthController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("user") UserDto user) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String performRegister(@ModelAttribute("user") UserDto user, BindingResult result) {
        if (result.hasErrors())
            return "auth/register";

        registerService.register(user);

        return "redirect:/auth/login";
    }

}
