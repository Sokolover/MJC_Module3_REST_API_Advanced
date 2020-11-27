package com.epam.esm.sokolov.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CertificateAppException extends RuntimeException {

    private HttpStatus statusCode;
    private Class<?> classThrewException;

    public CertificateAppException(String message, HttpStatus statusCode, Class<?> exceptionalClass) {
        super(message);
        this.statusCode = statusCode;
        this.classThrewException = exceptionalClass;
    }

    public CertificateAppException(String message) {
        super(message);
    }
}