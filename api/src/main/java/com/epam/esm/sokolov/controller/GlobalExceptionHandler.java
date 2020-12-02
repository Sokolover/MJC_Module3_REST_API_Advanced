package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.exception.CertificateAppException;
import com.epam.esm.sokolov.exception.ErrorResponse;
import com.epam.esm.sokolov.service.certificate.GiftCertificateServiceImpl;
import com.epam.esm.sokolov.service.order.OrderServiceImpl;
import com.epam.esm.sokolov.service.security.AuthenticationServiceImpl;
import com.epam.esm.sokolov.service.tag.TagServiceImpl;
import com.epam.esm.sokolov.service.user.UserServiceImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.sokolov.constants.CommonAppConstants.ERROR_MESSAGE;

@RestControllerAdvice
class GlobalExceptionHandler {

    private static final String TAG_ERROR_CODE = "01";
    private static final String GIFT_CERTIFICATE_ERROR_CODE = "02";
    private static final String USER_ERROR_CODE = "03";
    private static final String ORDER_ERROR_CODE = "04";
    private static final String AUTHENTICATION_ERROR_CODE = "05";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse defaultErrorHandler(Exception e) {
        return new ErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse errorResponse = new ErrorResponse();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        errorResponse.setErrorMessages(errors);
        errorResponse.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return errorResponse;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleJsonMappingException(HttpMessageNotReadableException e) {
        return new ErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse defaultErrorHandler() {
        return new ErrorResponse("Access is denied", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentTypeHandler(MethodArgumentTypeMismatchException e) {
        return new ErrorResponse("Bad argument in URI path variable", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse requiredParamHandler(MissingServletRequestParameterException e) {
        return new ErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse errorHandler() {
        String message = "Could not create-update entity: there aren't such user or giftCertificate or tag";
        return new ErrorResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CertificateAppException.class)
    public ResponseEntity<ErrorResponse> handleEntityException(CertificateAppException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessages(Collections.singletonMap(ERROR_MESSAGE, e.getMessage()));

        if (e.getClassThrewException() == TagServiceImpl.class || e.getClassThrewException() == TagController.class) {
            errorResponse.setErrorCode(e.getHttpStatus().value() + TAG_ERROR_CODE);
        }

        if (e.getClassThrewException() == GiftCertificateServiceImpl.class) {
            errorResponse.setErrorCode(e.getHttpStatus().value() + GIFT_CERTIFICATE_ERROR_CODE);
        }

        if (e.getClassThrewException() == UserServiceImpl.class) {
            errorResponse.setErrorCode(e.getHttpStatus().value() + USER_ERROR_CODE);
        }

        if (e.getClassThrewException() == OrderServiceImpl.class) {
            errorResponse.setErrorCode(e.getHttpStatus().value() + ORDER_ERROR_CODE);
        }

        if(e.getClassThrewException() == AuthenticationServiceImpl.class){
            errorResponse.setErrorCode(e.getHttpStatus().value() + AUTHENTICATION_ERROR_CODE);
        }

        HttpStatus httpStatus = e.getHttpStatus();
        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }
}
