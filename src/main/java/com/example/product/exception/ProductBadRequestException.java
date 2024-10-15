package com.example.product.exception;

public class ProductBadRequestException extends RuntimeException{

    public ProductBadRequestException(String message) {
        super(message);
    }
}
