package ru.yakaska.tenki.service;

import ru.yakaska.tenki.payload.auth.LoginDto;
import ru.yakaska.tenki.payload.auth.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

}
