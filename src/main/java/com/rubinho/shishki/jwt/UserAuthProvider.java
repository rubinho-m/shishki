package com.rubinho.shishki.jwt;

import com.rubinho.shishki.model.Role;
import org.springframework.security.core.Authentication;

public interface UserAuthProvider {
    String createToken(String login, Role role);

    String getLoginFromJwt(String token);

    Authentication validateToken(String token);
}
