package com.rubinho.shishki.jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    public String escapeToken(String token) {
        return token.split(" ")[1];
    }
}
