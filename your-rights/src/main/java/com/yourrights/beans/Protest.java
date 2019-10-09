package com.yourrights.beans;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Protest implements Serializable{

    private static final long serialVersionUID = -871458686480719400L;
    
    @JsonInclude(Include.NON_NULL)
    private long id;
    @JsonInclude(Include.NON_NULL)
    private String name;
    @JsonInclude(Include.NON_NULL)
    private String city;
    @JsonInclude(Include.NON_NULL)
    private String whoDefends;
    @JsonInclude(Include.NON_NULL)
    private String promotedBy;
    @JsonInclude(Include.NON_NULL)
    @JsonFormat(pattern = Constants.FORMAT_DD_MM_YYYY)
    private Date date;
    // Usuario logado
    @JsonInclude(Include.NON_NULL)
    private String area;
    @JsonInclude(Include.NON_NULL)
    @JsonFormat(pattern = Constants.FORMAT_HH_MM)
    private Date time;
	
	
}
