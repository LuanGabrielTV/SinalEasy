package com.sinalez.sinaleasy_back.exceptions.customExceptions.cityExceptions;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException() {
        super("Cidade ainda não registrada!");
    }

    public CityNotFoundException(String message) {
        super(message);
    }
}