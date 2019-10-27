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
public class SearchRequest implements Serializable {

    private static final long serialVersionUID = -6647255806255032146L;

    @JsonInclude(Include.NON_NULL)
    private String city;
    @JsonInclude(Include.NON_NULL)
    private String country;
    @JsonInclude(Include.NON_NULL)
    @JsonFormat(pattern = Constants.FORMAT_DD_MM_YYYY)
    private Date date;
    @JsonInclude(Include.NON_NULL)
    private String area;
    @JsonInclude(Include.NON_NULL)
    private String month;
}
