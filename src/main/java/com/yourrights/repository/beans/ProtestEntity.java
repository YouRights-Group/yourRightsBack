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
@Table(name = "protests")
@Getter
@Setter
public class ProtestEntity implements Serializable {
    private static final long serialVersionUID = -2343243243242432341L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "whoDefends")
    private String whoDefends;
    @Column(name = "promotedBy")
    private String promotedBy;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "area")
    private String area;
    @Column(name = "time")
    private Date time;
    @Column(name = "document")
    private byte[] document;
    @Column(name = "protestsType")
    private String protestsType;
    @Column(name = "userType")
    private String userType;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "protests_locations", joinColumns = { @JoinColumn(name = "protest_id") }, inverseJoinColumns = {
	    @JoinColumn(name = "location_id") })
    private Set<LocationEntity> locationsProtest = new HashSet<>();

}