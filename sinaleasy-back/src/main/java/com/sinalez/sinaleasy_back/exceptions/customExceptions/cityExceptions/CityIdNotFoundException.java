package com.sinalez.sinaleasy_back.exceptions.customExceptions.cityExceptions;

public class CityIdNotFoundException extends RuntimeException {
    public CityIdNotFoundException() {
        super("O ID da cidade não foi enviado");
    }

    public CityIdNotFoundException(String message) {
        super(message);
    }
}