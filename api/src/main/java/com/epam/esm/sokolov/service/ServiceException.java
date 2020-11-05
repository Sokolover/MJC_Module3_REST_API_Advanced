package com.epam.esm.sokolov.service;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

    private HttpStatus statusCode;
    private Class<?> repositoryClass;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ServiceException(String message, HttpStatus statusCode, Class<?> repositoryClass) {
        super(message);
        this.statusCode = statusCode;
        this.repositoryClass = repositoryClass;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public Class<?> getRepositoryClass() {
        return repositoryClass;
    }
}
