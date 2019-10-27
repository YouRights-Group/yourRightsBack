package com.yourrights.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yourrights.beans.User;
import com.yourrights.beans.UserResponse;
import com.yourrights.constants.Constants;
import com.yourrights.services.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(Constants.LOGIN)
    public UserResponse login(@RequestBody User user) {

	String token = getJWTToken(user.getEmail());

	userService.login(user, token);

	UserResponse response = new UserResponse();
	response.setToken(token);
	return response;

    }

    @PostMapping(Constants.SIGN_UP)
    public void signUp(@RequestBody User user) {
	userService.saveUser(user);
    }

    private String getJWTToken(String username) {
	String secretKey = "mySecretKey";
	List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

	String token = Jwts.builder().setId("softtekJWT").setSubject(username)
		.claim("authorities",
			grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + 600000))
		.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

	return token;
    }
}