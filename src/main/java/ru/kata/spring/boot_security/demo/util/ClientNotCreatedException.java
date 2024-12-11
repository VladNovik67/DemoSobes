package ru.kata.spring.boot_security.demo.util;

public class ClientNotCreatedException extends RuntimeException{

    public ClientNotCreatedException(String message) {
        super(message);
    }
}
