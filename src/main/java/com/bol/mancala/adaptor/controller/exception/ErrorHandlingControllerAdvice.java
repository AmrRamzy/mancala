package com.bol.mancala.adaptor.controller.exception;

import com.bol.mancala.usecase.exception.GameException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;


@ControllerAdvice
public class ErrorHandlingControllerAdvice {

	@ExceptionHandler(GameException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ValidationErrorResponse handleGameException(GameException e) {

		return new ValidationErrorResponse("Game Error", e.getMessage());
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ValidationErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {

		return new ValidationErrorResponse("player1Name/player2Name parameters are mandatory", e.getMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ValidationErrorResponse handleConstraintViolationException(ConstraintViolationException e) {

		return new ValidationErrorResponse("index must be between 1 & 6", e.getMessage());
	}


	
	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ValidationErrorResponse handleDataAccessException(DataAccessException e) {

		return new ValidationErrorResponse("DataBase error", e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	ValidationErrorResponse handleException(Exception e) {

		return new ValidationErrorResponse("something went wrong please try again", e.getMessage());
	}

}
