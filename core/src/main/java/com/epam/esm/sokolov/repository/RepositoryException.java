package com.epam.esm.sokolov.repository;

import org.springframework.http.HttpStatus;

public class RepositoryException extends RuntimeException {

    private HttpStatus statusCode;
    private Class<?> repositoryClass;

    public RepositoryException() {
        super();
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public RepositoryException(String message, HttpStatus statusCode, Class<?> repositoryClass) {
        super(message);
        this.statusCode = statusCode;
        this.repositoryClass = repositoryClass;
    }

    public RepositoryException(String message, Throwable cause) {
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
