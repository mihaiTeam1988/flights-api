package com.wp.shop.exception.handler;

import com.wp.shop.exception.BadRequestException;
import com.wp.shop.exception.ConflictException;
import com.wp.shop.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class HttpExceptionHandlerTest {

    @Mock
    private MethodArgumentNotValidException exception;

    @Mock
    private MethodArgumentTypeMismatchException mismatchException;

    @Mock
    private BeanPropertyBindingResult bindingResult;

    @InjectMocks
    private HttpExceptionHandler handler = new HttpExceptionHandler();

    @Test
    public void shouldHandleBadRequestException() {
        ResponseEntity<HttpExceptionResponse> response = handler.handleBadRequestException(new BadRequestException("error"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals("error", response.getBody().getMessage());
    }

    @Test
    public void shouldHandleMethodArgumentException() {

        String field = "name";
        String errorMessage = "is missing";

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors())
                .thenReturn(Arrays.asList(new FieldError("object", field, errorMessage)));

        ResponseEntity<HttpExceptionResponse> response = handler.handleMethodArgumentException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        HttpExceptionResponse exceptionResponse = response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), exceptionResponse.getStatus());
        assertEquals(1, exceptionResponse.getErrors().size());
        assertEquals(String.format("Parameter %s %s", field, errorMessage), exceptionResponse.getErrors().iterator().next());
    }

    @Test
    public void shouldHandleMethodArgumentTypeMismatchException() {

        when(mismatchException.getMessage()).thenReturn("mismatch error");

        ResponseEntity<HttpExceptionResponse> response = handler.handleMethodArgumentTypeMismatchException(mismatchException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals("mismatch error", response.getBody().getMessage());
    }

    @Test
    public void shouldHandleNotFoundException() {

        ResponseEntity<HttpExceptionResponse> response = handler.handleNotFoundException(new NotFoundException("error"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals("error", response.getBody().getMessage());
    }

    @Test
    public void shouldHandleConflictException() {

        ResponseEntity<HttpExceptionResponse> response = handler.handleConflictException(new ConflictException("error"));

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatus());
        assertEquals("error", response.getBody().getMessage());
    }

    @Test
    public void shouldHandleGenericException() {

        ResponseEntity<HttpExceptionResponse> response = handler.handleGenericException(new Exception("error"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertEquals("error", response.getBody().getMessage());
    }
}
