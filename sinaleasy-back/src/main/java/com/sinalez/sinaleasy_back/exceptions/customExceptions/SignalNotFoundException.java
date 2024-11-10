package com.sinalez.sinaleasy_back.exceptions.customExceptions;

public class SignalNotFoundException extends RuntimeException {
    public SignalNotFoundException() {
        super("Sinal não encontrado!");
    }

    public SignalNotFoundException(String message) {
        super(message);
    }
    
}
