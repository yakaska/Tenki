package ru.yakaska.tenki.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/")
    public String index() {
        return "user level access";
    }

}
