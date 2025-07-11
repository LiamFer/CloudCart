package com.liamfer.CloudCart.exceptions;

public class ProductInOrderException extends RuntimeException {
    public ProductInOrderException(String message) {
        super(message);
    }
}
