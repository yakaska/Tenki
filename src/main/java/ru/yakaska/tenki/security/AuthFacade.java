package ru.yakaska.tenki.security;

import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;

@Component
public class AuthFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
