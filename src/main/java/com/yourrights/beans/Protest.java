package com.yourrights.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(value = "nameProtest")
    private String name;
    @JsonInclude(Include.NON_NULL)
    @JsonProperty(value = "cityProtest")
    private String city;
    @JsonInclude(Include.NON_NULL)
    @JsonProperty(value = "countryProtest")
    private String country;
    @JsonInclude(Include.NON_NULL)
    @JsonProperty(value = "defenseSectorProtest")
    private String defenseSector;
    @JsonInclude(Include.NON_NULL)
    @JsonProperty(value = "promotedByProtest")
    private String promotedBy;
    @JsonInclude(Include.NON_NULL)
    @JsonFormat(pattern = Constants.FORMAT_DD_MM_YYYY, timezone = "Europe/Madrid")
    @JsonProperty(value = "dateProtest")
    private Date date;
    @JsonInclude(Include.NON_NULL)
    @JsonProperty(value = "areaProtest")
    private String area;
    @JsonInclude(Include.NON_NULL)
    @JsonFormat(pattern = Constants.FORMAT_HH_MM, timezone = "Europe/Madrid")
    @JsonProperty(value = "timeProtest")
    private Date time;
    @JsonInclude(Include.NON_NULL)
    @JsonProperty(value = "monthProtest")
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
