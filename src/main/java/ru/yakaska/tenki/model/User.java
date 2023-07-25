package ru.yakaska.tenki.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory.")
    private String username;
    @NotBlank(message = "Password is mandatory.")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User() {

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
