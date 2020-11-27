package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.exception.CertificateAppException;
import com.epam.esm.sokolov.exception.ControllerException;
import com.epam.esm.sokolov.exception.FilterException;
import com.epam.esm.sokolov.repository.order.OrderRepositoryImpl;
import com.epam.esm.sokolov.repository.tag.TagRepositoryImpl;
import com.epam.esm.sokolov.repository.user.UserRepositoryImpl;
import com.epam.esm.sokolov.service.certificate.GiftCertificateServiceImpl;
import com.epam.esm.sokolov.service.order.OrderServiceImpl;
import com.epam.esm.sokolov.service.tag.TagServiceImpl;
import com.epam.esm.sokolov.service.user.UserServiceImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
class GlobalExceptionHandlerHibernate {

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

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> requiredRequestParamHandler(MissingServletRequestParameterException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, e.getMessage());
        errorMap.put(ERROR_CODE, String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return errorMap;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> errorHandler() {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, "Could not create-update entity: there aren't such user or giftCertificate or tag");
        errorMap.put(ERROR_CODE, String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return errorMap;
    }

    @ExceptionHandler(FilterException.class)
    public ResponseEntity<Map<String, String>> handleFilterException(FilterException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)//todo search for an appropriate status code
                .body(errorMap);
    }

    @ExceptionHandler(ControllerException.class)
    public ResponseEntity<Map<String, String>> handleControllerException(ControllerException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, e.getMessage());
        errorMap.put(ERROR_CODE, HttpStatus.NOT_FOUND.value() + TAG_ERROR_CODE);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorMap);
    }


    @ExceptionHandler(CertificateAppException.class)
    public ResponseEntity<Map<String, String>> handleEntityException(CertificateAppException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, e.getMessage());

        if (e.getClassThrewException() == TagServiceImpl.class
                || e.getClassThrewException() == TagRepositoryImpl.class) {
            errorMap.put(ERROR_CODE, e.getStatusCode().value() + TAG_ERROR_CODE);
        }

        if (e.getClassThrewException() == GiftCertificateServiceImpl.class
                || e.getClassThrewException() == GiftCertificateRepositoryImpl.class) {
            errorMap.put(ERROR_CODE, e.getStatusCode().value() + GIFT_CERTIFICATE_ERROR_CODE);
        }

        if (e.getClassThrewException() == UserServiceImpl.class
                || e.getClassThrewException() == UserRepositoryImpl.class) {
            errorMap.put(ERROR_CODE, e.getStatusCode().value() + USER_ERROR_CODE);
        }

        if (e.getClassThrewException() == OrderServiceImpl.class
                || e.getClassThrewException() == OrderRepositoryImpl.class) {
            errorMap.put(ERROR_CODE, e.getStatusCode().value() + ORDER_ERROR_CODE);
        }

        return ResponseEntity
                .status(e.getStatusCode())
                .body(errorMap);
    }
}
