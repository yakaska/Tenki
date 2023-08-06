package ru.yakaska.tenki.payload.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class LoginDto {

    private String username;

    private String password;

}
