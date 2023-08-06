package ru.yakaska.tenki.service;

import ru.yakaska.tenki.payload.auth.*;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

}
