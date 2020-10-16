package com.cognixia.jump.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * The GlobalExceptionHandler for this application.
 * @author David Morales
 * @version v1 (10/14/2020)
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * The handler for the custom exception for a resource that was not found.
	 * @author David Morales
	 * @param ex the exception being thrown
	 * @param request the current web request
	 * @return ResponseEntity - the error details 
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundExceptionHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getLocalizedMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	/**
	 * The handler for the custom exception for a resource that already exists.
	 * @author David Morales
	 * @param ex the exception being thrown
	 * @param request the current web request
	 * @return ResponseEntity - the error details 
	 */
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<?> resourceAlreadyExistsExceptionHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getLocalizedMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}
	/**
	 * The handler an exception.
	 * @author David Morales
	 * @param ex the exception being thrown
	 * @param request the current web request
	 * @return ResponseEntity - the error details 
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getLocalizedMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
