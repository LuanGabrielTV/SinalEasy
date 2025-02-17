package com.sinalez.sinaleasy_back.exceptions.customExceptions.signalException;

public class SignalNotFoundException extends RuntimeException {
    public SignalNotFoundException() {
        super("Sinal não encontrado!");
    }

    public SignalNotFoundException(String message) {
        super(message);
    }
    
}
