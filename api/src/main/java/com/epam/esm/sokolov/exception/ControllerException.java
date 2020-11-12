package com.epam.esm.sokolov.exception;

import org.springframework.http.HttpStatus;

public class ControllerException extends CertificateAppException {

    public ControllerException(String message, HttpStatus statusCode, Class<?> repositoryClass) {
        super(message, statusCode, repositoryClass);
    }
}
