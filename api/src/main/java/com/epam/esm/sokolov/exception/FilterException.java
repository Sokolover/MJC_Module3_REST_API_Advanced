package com.epam.esm.sokolov.exception;

import org.springframework.http.HttpStatus;

public class FilterException extends CertificateAppException {

    public FilterException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
