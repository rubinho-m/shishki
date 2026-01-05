package com.rubinho.shishki.exceptions;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String login) {
        super("Account %s not found".formatted(login));
    }
}
