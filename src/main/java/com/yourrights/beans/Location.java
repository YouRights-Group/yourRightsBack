package com.yourrights.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class Location implements Serializable {

    private static final long serialVersionUID = 3518600570306306242L;

    double latitude;
    double longitude;
    long pointNumber;
}
