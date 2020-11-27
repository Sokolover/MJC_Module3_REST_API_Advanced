package com.epam.esm.sokolov.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends CertificateAppException {

    public ServiceException(String message, HttpStatus statusCode, Class<?> exceptionalClass) {
        super(message, statusCode, exceptionalClass);
    }

    public ServiceException(String message) {
        super(message);
    }
}
