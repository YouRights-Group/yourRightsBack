package com.yourrights.repository.beans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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

    @Column(name = "password")
    private String password;
    @Column(name = "token")
    private String token;
    @Id
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "id")
    private Set<ProtestEntity> favouritesProtests;
    @OneToMany(mappedBy = "id")
    private Set<ProtestEntity> myProtests;
}
