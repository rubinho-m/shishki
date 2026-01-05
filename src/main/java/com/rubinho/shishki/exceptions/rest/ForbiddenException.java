package com.rubinho.shishki.exceptions.rest;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public class ForbiddenException extends ResponseStatusException {
    public ForbiddenException(@Nullable String reason) {
        super(HttpStatus.FORBIDDEN, reason);
    }

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN);
    }
}
