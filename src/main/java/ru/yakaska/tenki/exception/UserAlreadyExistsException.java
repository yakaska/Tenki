package ru.yakaska.tenki.exception;

import org.springframework.http.*;
import org.springframework.web.server.*;

public class UserAlreadyExistsException extends ResponseStatusException {

    public UserAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
