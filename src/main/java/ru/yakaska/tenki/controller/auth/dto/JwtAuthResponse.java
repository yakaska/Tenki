package ru.yakaska.tenki.controller.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponse {

    private String accessToken;

    private String tokenType = "Bearer";

}
