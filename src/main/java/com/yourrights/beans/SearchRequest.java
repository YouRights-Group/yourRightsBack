package com.yourrights.beans;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yourrights.constants.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class SearchRequest implements Serializable {

    private static final long serialVersionUID = -6647255806255032146L;

    
    private String city;
    private String country;
    @JsonFormat(pattern = Constants.FORMAT_DD_MM_YYYY)
    private Date from;
    @JsonFormat(pattern = Constants.FORMAT_DD_MM_YYYY)
    private Date to;
    private String area;
    private String month;
    private int pageIndex;
}
