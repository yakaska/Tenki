package ru.yakaska.tenki.exception;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
}
