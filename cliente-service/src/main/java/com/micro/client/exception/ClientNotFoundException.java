package com.micro.client.exception;

public class ClientNotFoundException extends  RuntimeException {

    public ClientNotFoundException(Long id) {
        super("Client with id " + id + " not found");
    }
    public ClientNotFoundException(String message) {
        super(message);
    }
}
