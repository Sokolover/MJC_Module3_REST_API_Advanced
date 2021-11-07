package com.epam.esm.sokolov.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CertificateAppException extends RuntimeException {

    private HttpStatus httpStatus;
    private Class<?> classThrewException;

    public CertificateAppException(String message, HttpStatus httpStatus, Class<?> exceptionalClass) {
        super(message);
        this.httpStatus = httpStatus;
        this.classThrewException = exceptionalClass;
    }

    public CertificateAppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CertificateAppException(String message) {
        super(message);
    }
}