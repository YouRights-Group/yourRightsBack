package com.yourrights.repository.beans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -8080222168695030393L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "password")
    private String password;
    @Column(name = "token", length = 1024)
    private String token;
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "id")
    private Set<ProtestEntity> favouritesProtests;
    @OneToMany(mappedBy = "id")
    private Set<ProtestEntity> myProtests;
}
