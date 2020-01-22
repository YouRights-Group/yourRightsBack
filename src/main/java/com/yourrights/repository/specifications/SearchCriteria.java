package com.yourrights.repository.specifications;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteria {
    
    public SearchCriteria(String key, String operation, Date from, Date to) {
	this.key = key;
	this.operation = operation;
	this.from = from;
	this.to = to;
    }
    
    public SearchCriteria(String key, String operation, String value) {
	this.key = key;
	this.operation = operation;
	this.value = value;
    }

    private String key;
    private String operation;
    private Object value;
    private Date from;
    private Date to;
}