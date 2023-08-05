package ru.yakaska.tenki;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.*;
import ru.yakaska.tenki.entity.*;
import ru.yakaska.tenki.repository.*;

import java.util.*;

@SpringBootApplication
public class TenkiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenkiApplication.class, args);
    }


}
