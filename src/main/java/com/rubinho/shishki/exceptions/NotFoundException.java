package com.rubinho.shishki.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException(@Nullable String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
