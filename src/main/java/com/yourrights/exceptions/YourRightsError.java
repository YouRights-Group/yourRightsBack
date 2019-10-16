package com.yourrights.exceptions;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YourRightsError implements Serializable {

    private static final long serialVersionUID = -8762159765255905006L;

    private String type;
    private String code;
    private String error;

    public YourRightsError(String type, String code, String error) {
	this.type = type;
	this.code = code;
	this.error = error;
    }

}
