package com.yourrights.repository.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PROTESTS")
@Getter
@Setter
public class ProtestEntity implements Serializable {
    private static final long serialVersionUID = -2343243243242432341L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CITY")
    private String city;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "DEFENSESECTOR")
    private String defenseSector;
    @Column(name = "PROMOTEDBY")
    private String promotedBy;
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "TIME")
    @Temporal(TemporalType.TIME)
    private Date time;
    @Column(name = "AREA")
    private String area;
    @Column(name = "DOCUMENT")
    private byte[] document;
    @Column(name = "PROTESTTYPE")
    private String protestType;
    @Column(name = "USERTYPE")
    private String userType;
    @Column(name = "USERID")
    private long userId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "protests_locations", joinColumns = { @JoinColumn(name = "protest_id") }, inverseJoinColumns = {
	    @JoinColumn(name = "location_id") })
    private Set<LocationEntity> locationsProtest = new HashSet<>();

}