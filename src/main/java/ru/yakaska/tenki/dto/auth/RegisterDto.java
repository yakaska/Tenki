package ru.yakaska.tenki.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDto {

    private String username;

    private String password;

    private String matchingPassword;

}
