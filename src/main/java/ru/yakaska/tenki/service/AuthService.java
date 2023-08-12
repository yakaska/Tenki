package ru.yakaska.tenki.service;

import ru.yakaska.tenki.controller.auth.dto.LoginDto;
import ru.yakaska.tenki.controller.auth.dto.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

}
