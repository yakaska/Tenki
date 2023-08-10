package ru.yakaska.tenki.service;

import ru.yakaska.tenki.controller.auth.dto.*;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

}
