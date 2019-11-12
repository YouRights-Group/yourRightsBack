package com.yourrights.repository.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "LOCATIONS")
@Getter
@Setter
public class LocationEntity implements Serializable {

    private static final long serialVersionUID = -4287293248059189043L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "LATITUDE")
    private double latitude;
    @Column(name = "LONGITUDE")
    private double longitude;
    @Column(name = "PROTESTID")
    private long protestId;
    @Column(name = "POINTNUMBER")
    private long pointNumber;
}
