package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.exception.CertificateAppException;
import com.epam.esm.sokolov.exception.RepositoryException;
import com.epam.esm.sokolov.exception.ServiceException;
import com.epam.esm.sokolov.repository.GiftCertificateRepositoryImpl;
import com.epam.esm.sokolov.repository.OrderRepositoryImpl;
import com.epam.esm.sokolov.repository.TagRepositoryImpl;
import com.epam.esm.sokolov.repository.UserRepositoryImpl;
import com.epam.esm.sokolov.service.certificate.GiftCertificateServiceImpl;
import com.epam.esm.sokolov.service.order.OrderServiceImpl;
import com.epam.esm.sokolov.service.tag.TagServiceImpl;
import com.epam.esm.sokolov.service.user.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
class GlobalExceptionHandler {

    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";
    private static final String TAG_ERROR_CODE = "01";
    private static final String GIFT_CERTIFICATE_ERROR_CODE = "02";
    private static final String USER_ERROR_CODE = "03";
    private static final String ORDER_ERROR_CODE = "04";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> defaultErrorHandler(Exception e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, e.getMessage());
        errorMap.put(ERROR_CODE, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return errorMap;
    }

    @ExceptionHandler({ServiceException.class, RepositoryException.class})
    public ResponseEntity<Map<String, String>> handleRepositoryException(CertificateAppException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, e.getMessage());

        if (e.getRepositoryClass() == TagServiceImpl.class || e.getRepositoryClass() == TagRepositoryImpl.class) {
            errorMap.put(ERROR_CODE, e.getStatusCode().value() + TAG_ERROR_CODE);
        }

        if (e.getRepositoryClass() == GiftCertificateServiceImpl.class || e.getRepositoryClass() == GiftCertificateRepositoryImpl.class) {
            errorMap.put(ERROR_CODE, e.getStatusCode().value() + GIFT_CERTIFICATE_ERROR_CODE);
        }

        if (e.getRepositoryClass() == UserServiceImpl.class || e.getRepositoryClass() == UserRepositoryImpl.class) {
            errorMap.put(ERROR_CODE, e.getStatusCode().value() + USER_ERROR_CODE);
        }

        if (e.getRepositoryClass() == OrderServiceImpl.class || e.getRepositoryClass() == OrderRepositoryImpl.class) {
            errorMap.put(ERROR_CODE, e.getStatusCode().value() + ORDER_ERROR_CODE);
        }

        return ResponseEntity
                .status(e.getStatusCode())
                .body(errorMap);
    }
}
