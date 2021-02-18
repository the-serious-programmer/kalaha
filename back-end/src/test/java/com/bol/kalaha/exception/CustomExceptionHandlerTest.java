package com.bol.kalaha.exception;

import com.bol.kalaha.dto.ExceptionDTO;
import org.hibernate.TypeMismatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomExceptionHandlerTest {
    private static final String INTERNAL_SERVER_ERROR =
        "A technical error occurred which could not be resolved by the service";
    private static final String CONVERT_ERROR_MESSAGE =
        "The given request could not be parsed correctly according to the API definition";
    private static final String NOT_FOUND_ERROR_MESSAGE = "Requested route was not found";

    private final MockHttpServletRequest servletRequest = new MockHttpServletRequest("GET", "path=/api/mock");
    private final MockHttpServletResponse servletResponse = new MockHttpServletResponse();
    private final WebRequest request = new DispatcherServletWebRequest(this.servletRequest, this.servletResponse);
    private CustomExceptionHandler customExceptionHandler;

    @BeforeEach
    public void init() {
        this.customExceptionHandler = new CustomExceptionHandler();
    }

    @Test
    public void givenExceptionShouldReturnInternalServerError() {
        final Exception exception = new RuntimeException();
        final ResponseEntity<Object> responseEntity = customExceptionHandler.handleGlobalException(exception, request);
        assertExceptionResponse(responseEntity, INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void givenGameExceptionShouldReturnInternalServerError() {
        final GameException exception = new GameException(GameException.NOT_FOUND);
        final ResponseEntity<Object> responseEntity = customExceptionHandler.handleGlobalException(exception, request);
        assertExceptionResponse(responseEntity, INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void givenPlayerExceptionShouldReturnInternalServerError() {
        final PlayerException exception = new PlayerException(PlayerException.CURRENT_PLAYER_NOT_FOUND);
        final ResponseEntity<Object> responseEntity = customExceptionHandler.handleGlobalException(exception, request);
        assertExceptionResponse(responseEntity, INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void givenPitExceptionShouldReturnInternalServerError() {
        final PitException exception = new PitException(PitException.NOT_FOUND);
        final ResponseEntity<Object> responseEntity = customExceptionHandler.handleGlobalException(exception, request);
        assertExceptionResponse(responseEntity, INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void givenHttpMessageConversionExceptionShouldReturnBadRequest() {
        final HttpMessageConversionException exception = new HttpMessageConversionException("Bad Request");
        final ResponseEntity<Object> responseEntity =
            customExceptionHandler.handleConversionException(exception, request);
        assertExceptionResponse(responseEntity, CONVERT_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void givenMethodArgumentTypeMismatchExceptionShouldReturnBadRequest() {
        final TypeMismatchException exception = new TypeMismatchException("");
        final ResponseEntity<Object> responseEntity =
            customExceptionHandler.handleConversionException(exception, request);
        assertExceptionResponse(responseEntity, CONVERT_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void givenNoHandlerFoundExceptionShouldReturnNotFound() {
        final NoHandlerFoundException exception = new NoHandlerFoundException("", "", HttpHeaders.EMPTY);
        final ResponseEntity<Object> responseEntity = customExceptionHandler.handleNoHandlerFoundException(
            exception, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR, request);
        assertExceptionResponse(responseEntity, NOT_FOUND_ERROR_MESSAGE, HttpStatus.NOT_FOUND);
    }

    private void assertExceptionResponse(final ResponseEntity<Object> responseEntity,
        final String expectedMessage,
        final HttpStatus httpStatus) {
        assertEquals(httpStatus, responseEntity.getStatusCode());
        assertResponseMessage(responseEntity, expectedMessage);
    }

    private void assertResponseMessage(final ResponseEntity<Object> responseEntity,
        final String expectedMessage) {
        final Object responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        final ExceptionDTO exceptionDTO = (ExceptionDTO) responseBody;
        assertEquals(expectedMessage, exceptionDTO.getMessage());
    }

}
