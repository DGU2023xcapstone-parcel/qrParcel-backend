package com.capstone.project.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String email){
        super(email + " Can Not Found Exception");
    }
}
