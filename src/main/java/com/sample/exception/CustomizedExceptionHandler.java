package com.sample.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception handler for dashboard services. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DashboardException.DimensionNotFoundException.class)
	protected ResponseEntity<Object> handleInvalidInputDataException(
			RuntimeException ex, WebRequest request) {
		String bodyOfResponse = ex.getMessage();
		return handleExceptionInternal(ex, bodyOfResponse, 
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
}
