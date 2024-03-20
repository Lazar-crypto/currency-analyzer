package com.razal.caback.exception;

import org.springframework.http.HttpStatus;

public record ExceptionResponse(int statusCode,  HttpStatus status, String msg){}
