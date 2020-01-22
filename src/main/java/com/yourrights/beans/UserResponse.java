package com.yourrights.beans;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse implements Serializable{

    private static final long serialVersionUID = 3986633035159859900L;

    private String token;
}
