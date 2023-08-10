package ru.yakaska.tenki.service;

import ru.yakaska.tenki.dto.auth.LoginDto;
import ru.yakaska.tenki.dto.auth.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

}
