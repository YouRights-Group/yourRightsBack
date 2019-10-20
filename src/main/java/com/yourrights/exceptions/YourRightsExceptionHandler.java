package com.yourrights.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class YourRightsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ProtestsException.class)
    protected ResponseEntity<YourRightsError> getProtestException(ProtestsException e) {

	YourRightsError error = new YourRightsError(e.getType(), e.getCode(), e.getMessage());
	ResponseEntity<YourRightsError> entity = new ResponseEntity<YourRightsError>(error, HttpStatus.OK);
	return entity;
    }
    
    @ExceptionHandler(value = UserException.class)
    protected ResponseEntity<YourRightsError> getUserException(UserException e) {

	YourRightsError error = new YourRightsError(e.getType(), e.getCode(), e.getMessage());
	ResponseEntity<YourRightsError> entity = new ResponseEntity<YourRightsError>(error, HttpStatus.OK);
	return entity;
    }
}
