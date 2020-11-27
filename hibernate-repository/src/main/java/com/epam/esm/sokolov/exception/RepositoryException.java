package com.epam.esm.sokolov.exception;

import org.springframework.http.HttpStatus;

public class RepositoryException extends CertificateAppException {

    public RepositoryException(String message, HttpStatus statusCode, Class<?> exceptionalClass) {
        super(message, statusCode, exceptionalClass);
    }
}