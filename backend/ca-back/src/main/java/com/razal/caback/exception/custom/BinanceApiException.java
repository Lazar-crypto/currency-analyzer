package com.razal.caback.exception.custom;

public class BinanceApiException extends RuntimeException{
    public BinanceApiException(String message) {
        super(message);
    }
}
