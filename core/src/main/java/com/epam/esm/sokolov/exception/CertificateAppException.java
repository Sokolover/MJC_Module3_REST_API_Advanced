package com.epam.esm.sokolov.exception;

import org.springframework.http.HttpStatus;

public class CertificateAppException extends RuntimeException {

    private HttpStatus statusCode;
    private Class<?> classThrewException;

    public CertificateAppException(String message, HttpStatus statusCode, Class<?> exceprionalClass) {
        super(message);
        this.statusCode = statusCode;
        this.classThrewException = exceprionalClass;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public Class<?> getClassThrewException() {
        return classThrewException;
    }
}