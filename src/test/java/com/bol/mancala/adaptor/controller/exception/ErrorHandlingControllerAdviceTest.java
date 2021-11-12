package com.bol.mancala.adaptor.controller.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ErrorHandlingControllerAdviceTest {

    private ErrorHandlingControllerAdvice errorHandlingControllerAdvice;

    @BeforeEach
    void init() {
        errorHandlingControllerAdvice = new ErrorHandlingControllerAdvice();
    }

    @Test
    @DisplayName("test exception ConstraintViolationException handling")
    void testHandleConstraintViolationException() {
        Set<? extends ConstraintViolation<?>> constraintViolations = new HashSet<ConstraintViolation<?>>();
        String message = "validation message";
        ConstraintViolationException e = new ConstraintViolationException(message, constraintViolations);
        ValidationErrorResponse validationErrorResponse = errorHandlingControllerAdvice
                .handleConstraintViolationException(e);
        assertEquals("index must be between 1 & 6", validationErrorResponse.getMessage());
    }


    @Test
    @DisplayName("test exception MissingServletRequestParameterException handling")
    void testHandleMissingServletRequestParameterException() {
        MissingServletRequestParameterException e = new MissingServletRequestParameterException("parameterName",
                "parameterType");
        ValidationErrorResponse validationErrorResponse = errorHandlingControllerAdvice
                .handleMissingServletRequestParameterException(e);
        assertEquals("player1Name/player2Name parameters are mandatory", validationErrorResponse.getMessage());
    }

    @Test
    @DisplayName("test exception DataAccessException handling")
    void testhandleDataAccessException() {
        ValidationErrorResponse validationErrorResponse = errorHandlingControllerAdvice
                .handleDataAccessException(new TypeMismatchDataAccessException("DB issue"));
        assertEquals("DataBase error", validationErrorResponse.getMessage());
    }

    @Test
    @DisplayName("test any other exception handling")
    void testHandleException() {
        ValidationErrorResponse validationErrorResponse = errorHandlingControllerAdvice
                .handleException(new NullPointerException());
        assertEquals("something went wrong please try again", validationErrorResponse.getMessage());
    }
}