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
@JsonInclude(Include.NON_NULL)
public class Protest implements Serializable {

    private static final long serialVersionUID = -871458686480719400L;

    private long id;
    @JsonProperty(value = "nameProtest")
    private String name;
    @JsonProperty(value = "cityProtest")
    private String city;
    @JsonProperty(value = "countryProtest")
    private String country;
    @JsonProperty(value = "defenseSectorProtest")
    private String defenseSector;
    @JsonProperty(value = "promotedByProtest")
    private String promotedBy;
    @JsonFormat(pattern = Constants.FORMAT_DD_MM_YYYY, timezone = "Europe/Madrid")
    @JsonProperty(value = "dateProtest")
    private Date date;
    @JsonProperty(value = "areaProtest")
    private String area;
    @JsonFormat(pattern = Constants.FORMAT_HH_MM, timezone = "Europe/Madrid")
    @JsonProperty(value = "timeProtest")
    private Date time;
    @JsonProperty(value = "monthProtest")
    private String month;
    private byte[] document;
    private ProtestType protestType;
    private UserType userType;
    private List<Location> locationsProtest;

}
