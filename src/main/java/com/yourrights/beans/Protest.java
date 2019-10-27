package com.yourrights.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yourrights.constants.Constants;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Protest implements Serializable {

    private static final long serialVersionUID = -871458686480719400L;

    @JsonInclude(Include.NON_NULL)
    private long id;
    @JsonInclude(Include.NON_NULL)
    private String name;
    @JsonInclude(Include.NON_NULL)
    private String city;
    @JsonInclude(Include.NON_NULL)
    private String country;
    @JsonInclude(Include.NON_NULL)
    private String whoDefends;
    @JsonInclude(Include.NON_NULL)
    private String promotedBy;
    @JsonInclude(Include.NON_NULL)
    @JsonFormat(pattern = Constants.FORMAT_DD_MM_YYYY)
    private Date date;
    @JsonInclude(Include.NON_NULL)
    private String area;
    @JsonInclude(Include.NON_NULL)
    @JsonFormat(pattern = Constants.FORMAT_HH_MM)
    private Date time;
    @JsonInclude(Include.NON_NULL)
    private String month;
    @JsonInclude(Include.NON_NULL)
    private byte[] document;
    @JsonInclude(Include.NON_NULL)
    private ProtestType protestType;
    @JsonInclude(Include.NON_NULL)
    private UserType userType;
    @JsonInclude(Include.NON_NULL)
    private List<Location> locationsProtest;

}
