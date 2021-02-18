package com.bol.kalaha.exception;

import com.bol.kalaha.dto.ExceptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final HttpHeaders HEADERS = new HttpHeaders();
    private static final String INTERNAL_SERVER_ERROR =
        "A technical error occurred which could not be resolved by the service";
    private static final String CONVERT_ERROR_MESSAGE =
        "The given request could not be parsed correctly according to the API definition";
    private static final String NOT_FOUND_ERROR_MESSAGE = "Requested route was not found";

    public CustomExceptionHandler() {
        HEADERS.setContentType(MediaType.APPLICATION_JSON);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleGlobalException(final Exception exception, final WebRequest webRequest) {
        log.error("Generic Exception caught ", exception);
        return handleException(exception, INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }

    @ExceptionHandler({HttpMessageConversionException.class, MethodArgumentTypeMismatchException.class})
    public final ResponseEntity<Object> handleConversionException(final Exception exception,
        final WebRequest webRequest) {
        log.error("Conversion Exception caught ", exception);
        return handleException(exception, CONVERT_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException exception,
        final HttpHeaders headers,
        final HttpStatus status,
        final WebRequest request) {
        log.error("NoHandlerFound Exception caught ", exception);
        return handleException(exception, NOT_FOUND_ERROR_MESSAGE, HttpStatus.NOT_FOUND, request);
    }

    private ResponseEntity<Object> handleException(final Exception exception,
        final String message,
        final HttpStatus httpStatus,
        final WebRequest webRequest) {
        return handleExceptionInternal(exception, message, HEADERS, httpStatus, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception exception,
        final Object body,
        final HttpHeaders headers,
        final HttpStatus status,
        final WebRequest request) {
        final ExceptionDTO exceptionDTO = new ExceptionDTO((String) body);
        return new ResponseEntity<>(exceptionDTO, headers, status);
    }
}
