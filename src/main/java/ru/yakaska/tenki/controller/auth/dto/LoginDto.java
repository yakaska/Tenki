package ru.yakaska.tenki.controller.auth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class LoginDto {

    private String username;

    private String password;

}
