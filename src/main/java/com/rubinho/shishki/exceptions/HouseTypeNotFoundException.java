package com.rubinho.shishki.exceptions;

public class HouseTypeNotFoundException extends Exception {
    public HouseTypeNotFoundException(String houseType) {
        super("House type %s not found".formatted(houseType));
    }
}
