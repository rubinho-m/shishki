package com.rubinho.shishki.exceptions;

public class HouseStatusNotFoundException extends Exception {
    public HouseStatusNotFoundException(String houseStatus) {
        super("House status %s not found".formatted(houseStatus));
    }
}
