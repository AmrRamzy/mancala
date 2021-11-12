package com.bol.mancala.adaptor.controller.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Setter
@Getter
@EqualsAndHashCode
@JsonInclude(Include. NON_NULL)
public class ValidationErrorResponse {

	private final String message;
	private final String errorDescription;
}
