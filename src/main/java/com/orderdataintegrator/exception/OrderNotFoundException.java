package com.orderdataintegrator.exception;

import java.util.NoSuchElementException;

public class OrderNotFoundException extends NoSuchElementException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
