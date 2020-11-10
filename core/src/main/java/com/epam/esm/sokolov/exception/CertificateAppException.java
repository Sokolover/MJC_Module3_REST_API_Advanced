package com.epam.esm.sokolov.exception;

import org.springframework.http.HttpStatus;

public class CertificateAppException extends RuntimeException {

    private HttpStatus statusCode;
    private Class<?> repositoryClass;

    public CertificateAppException(String message, HttpStatus statusCode, Class<?> repositoryClass) {
        super(message);
        this.statusCode = statusCode;
        this.repositoryClass = repositoryClass;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public Class<?> getRepositoryClass() {
        return repositoryClass;
    }
}