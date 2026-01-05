package com.rubinho.shishki.exceptions;

public class HouseNotFoundException extends Exception {
    public HouseNotFoundException(Long id) {
        super("House with id %d not found".formatted(id));
    }
}
