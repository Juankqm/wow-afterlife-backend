package com.afterlife.wow_afterlife_api.exception;

public class AccountAlreadyExistsException
        extends RuntimeException {

    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
