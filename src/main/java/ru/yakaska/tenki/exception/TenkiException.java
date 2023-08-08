package ru.yakaska.tenki.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TenkiException extends RuntimeException{

    private final HttpStatus status;
    private final String message;

    public TenkiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public TenkiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
