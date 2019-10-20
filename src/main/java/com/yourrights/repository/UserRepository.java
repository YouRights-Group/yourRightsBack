package com.yourrights.repository;


import org.springframework.data.repository.CrudRepository;

import com.yourrights.repository.beans.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long>{

    UserEntity findByUser(String user);
    
}
