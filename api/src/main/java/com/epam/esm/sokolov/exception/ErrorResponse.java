package com.epam.esm.sokolov.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ErrorResponse implements Serializable {

    private String errorMessage;
    private String errorCode;

    public ErrorResponse(CertificateAppException e) {
        this.errorMessage = e.getMessage();
        this.errorCode = String.valueOf(e.getHttpStatus().value());
    }

    public ErrorResponse(Exception e, HttpStatus httpStatus) {
        this.errorMessage = e.getMessage();
        this.errorCode = String.valueOf(httpStatus.value());
    }

    public ErrorResponse(String message, HttpStatus httpStatus) {
        this.errorMessage = message;
        this.errorCode = String.valueOf(httpStatus.value());
    }
}
