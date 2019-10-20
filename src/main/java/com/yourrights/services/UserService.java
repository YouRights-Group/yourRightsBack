package com.yourrights.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yourrights.beans.User;
import com.yourrights.constants.Constants;
import com.yourrights.exceptions.UserException;
import com.yourrights.repository.UserRepository;
import com.yourrights.repository.beans.UserEntity;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void saveUser(User user) {
	
	UserEntity userExisted = repository.findByUser(user.getUser());
	if(userExisted != null) {
	    throw new UserException(Constants.ERROR, Constants.USER_EXISTED, "User already exists");
	}
	
	UserEntity userEntity = new UserEntity();
	userEntity.setUser(user.getUser());
	userEntity.setPassword(user.getPassword());
	
	repository.save(userEntity);
	
    }

    public void login(User user) {
	UserEntity userExisted = repository.findByUser(user.getUser());
	if(userExisted == null) 
	{
	    throw new UserException(Constants.ERROR, Constants.USER_NOT_FOUND, "User not found");
	} else 
	{
	    if(!userExisted.getPassword().equals(user.getPassword()))
	    {
		throw new UserException(Constants.ERROR, Constants.USER_WRONG_PASSWORD, "User wrong password");
	    }
	}
    }
    
    
}
