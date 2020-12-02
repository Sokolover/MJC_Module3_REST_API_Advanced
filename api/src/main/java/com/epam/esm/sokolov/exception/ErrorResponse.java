package com.epam.esm.sokolov.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.sokolov.constants.CommonAppConstants.ERROR_MESSAGE;

@Data
@NoArgsConstructor
public class ErrorResponse implements Serializable {

    private Map<String, String> errorMessages = new HashMap<>();
    private String errorCode;

    public ErrorResponse(CertificateAppException e) {
        this.errorMessages.put(ERROR_MESSAGE, e.getMessage());
        this.errorCode = String.valueOf(e.getHttpStatus().value());
    }

    public ErrorResponse(Exception e, HttpStatus httpStatus) {
        this.errorMessages.put(ERROR_MESSAGE, e.getMessage());
        this.errorCode = String.valueOf(httpStatus.value());
    }

    public ErrorResponse(String message, HttpStatus httpStatus) {
        this.errorMessages.put(ERROR_MESSAGE, message);
        this.errorCode = String.valueOf(httpStatus.value());
    }
}
