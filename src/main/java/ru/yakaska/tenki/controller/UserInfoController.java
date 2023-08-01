package ru.yakaska.tenki.controller;

import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import ru.yakaska.tenki.security.*;

@Controller
public class UserInfoController {

    @GetMapping("/user")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TenkiUserDetails userDetails = (TenkiUserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());
        return "index";
    }

}
