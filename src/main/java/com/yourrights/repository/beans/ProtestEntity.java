package com.yourrights.repository.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "protest")
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
    @Column(name = "whoDefends")
    private String whoDefends;
    @Column(name = "promotedBy")
    private String promotedBy;
    @Column(name = "date")
    private Date date;
    @Column(name = "area")
    private String area;
    @Column(name = "time")
    private Date time;
    // TODO: Añadir blob para adjuntos
    // TODO: Añadir ubicación GPS
}