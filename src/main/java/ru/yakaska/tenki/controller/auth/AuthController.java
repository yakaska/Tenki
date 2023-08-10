package ru.yakaska.tenki.controller.auth;

import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.yakaska.tenki.controller.auth.dto.*;
import ru.yakaska.tenki.service.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterDto registerDto) {
        RegisterResponse response = new RegisterResponse(authService.register(registerDto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
