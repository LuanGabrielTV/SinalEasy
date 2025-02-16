package com.sinalez.sinaleasy_back.exceptions.customExceptions.userExceptions;

public class UserEmailIsBlank extends RuntimeException {
    public UserEmailIsBlank() {
        super("O usuário precisa inserir um email");
    }

    public UserEmailIsBlank(String message) {
        super(message);
    }
}
