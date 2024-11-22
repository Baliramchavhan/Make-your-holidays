package com.TravallingSystem.ExceptionHandeller;



public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
