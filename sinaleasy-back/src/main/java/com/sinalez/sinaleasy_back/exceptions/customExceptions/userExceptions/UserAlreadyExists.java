package com.sinalez.sinaleasy_back.exceptions.customExceptions.userExceptions;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists() {
        super("Esse usuário já foi registrado.");
    }

    public UserAlreadyExists(String message) {
        super(message);
    }
}
