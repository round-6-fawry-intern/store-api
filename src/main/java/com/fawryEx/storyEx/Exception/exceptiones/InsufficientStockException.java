package com.fawryEx.storyEx.Exception.exceptiones;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}