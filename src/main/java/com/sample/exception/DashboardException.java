package com.sample.exception;

import org.springframework.stereotype.Component;

/**
 * This class creates related exceptions for dashboard services. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Component
public class DashboardException extends Exception{

	public static RuntimeException throwException(String messageTemplate, String... args) {
		return new RuntimeException(messageTemplate);
	}

	public static class DimensionNotFoundException extends RuntimeException {
		public DimensionNotFoundException(String message) {
			super(message);
		}
	}

	public static class InvalidInputDataException extends RuntimeException {
		public InvalidInputDataException(String message) {
			super(message);
		}
	}

	public static RuntimeException throwException(ExceptionType exceptionType, String message) {
		if (ExceptionType.DIMENSION_NOT_FOUND_EXCEPTION.equals(exceptionType)) {
			return new DimensionNotFoundException(message);
		} else if (ExceptionType.INVALID_INPUT_DATA_EXCEPTION.equals(exceptionType)) {
			return new InvalidInputDataException(message);
		}
		return new RuntimeException(message);
	}

}

