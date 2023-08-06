package ru.yakaska.tenki.payload.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDto {

    private String username;

    private String password;

    private String matchingPassword;

}
