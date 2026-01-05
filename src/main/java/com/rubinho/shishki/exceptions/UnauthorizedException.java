package com.rubinho.shishki.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedException extends ResponseStatusException {
    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, "Bad credentials");
    }

    public UnauthorizedException(@Nullable String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }
}
