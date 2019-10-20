package com.yourrights.beans;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 5526277252448918664L;

    private String user;
    private String token;
}
