package ru.yakaska.tenki.controller.auth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDto {

    private String username;

    private String password;

    private String matchingPassword;

}
