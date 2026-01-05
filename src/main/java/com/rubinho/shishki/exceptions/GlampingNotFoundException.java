package com.rubinho.shishki.exceptions;

public class GlampingNotFoundException extends Exception {
    public GlampingNotFoundException(Long id) {
        super("Glamping with id %d not found".formatted(id));
    }
}
