package com.yourrights.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserException extends RuntimeException {

    private static final long serialVersionUID = -8577757429704528505L;
	
    private String type;
    private String code;
    private String message;
	
    public UserException(String type, String code, String message) {
	this.type = type;
	this.code = code;
	this.message = message;
    }

}
