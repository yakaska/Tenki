package ru.yakaska.tenki.payload.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponse {

    private String accessToken;

    private String tokenType = "Bearer";

}
