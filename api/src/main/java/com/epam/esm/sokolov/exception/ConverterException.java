package com.epam.esm.sokolov.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Sokolov_SA
 * @created 25.10.2021
 */
public class ConverterException extends CertificateAppException {

    public ConverterException(String message) {
        super(message);
    }
}
