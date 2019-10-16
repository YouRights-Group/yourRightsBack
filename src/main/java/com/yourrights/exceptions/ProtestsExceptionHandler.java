package com.yourrights.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ProtestsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ProtestsException.class)
    protected ResponseEntity<YourRightsError> getProtestException(ProtestsException e) {

	YourRightsError error = new YourRightsError(e.getType(), e.getCode(), e.getMessage());
	ResponseEntity<YourRightsError> entity = new ResponseEntity<YourRightsError>(error, HttpStatus.OK);
	return entity;
    }
}
