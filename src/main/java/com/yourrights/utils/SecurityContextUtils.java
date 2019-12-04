package com.yourrights.utils;

import java.util.Map;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yourrights.repository.beans.UserEntity;

public class SecurityContextUtils {

    public static UserEntity getUser() {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	Map<String, Object> info = (Map<String, Object>) ((AbstractAuthenticationToken) auth).getDetails();
	UserEntity userEntity = (UserEntity) info.get("user");
	return userEntity;
    }
}
