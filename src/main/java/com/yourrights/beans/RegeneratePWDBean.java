package com.yourrights.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegeneratePWDBean {

    private String token;
    private String newPassword;
}
