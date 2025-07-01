package com.liamfer.CloudCart.exceptions;

public class ProductNotEnoughInStockException extends RuntimeException {
    public ProductNotEnoughInStockException(String message) {
        super(message);
    }
}
