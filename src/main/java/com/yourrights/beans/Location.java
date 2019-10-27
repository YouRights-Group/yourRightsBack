package com.yourrights.beans;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location implements Serializable {

    private static final long serialVersionUID = 3518600570306306242L;

    double latitude;
    double longitude;
}
