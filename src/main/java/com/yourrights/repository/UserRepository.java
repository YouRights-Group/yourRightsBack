package com.yourrights.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yourrights.repository.beans.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    // TODO: PASAR A LA CACHÉ
    UserEntity findByToken(String token);

}
