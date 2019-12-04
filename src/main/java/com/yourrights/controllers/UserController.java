package com.yourrights.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yourrights.beans.User;
import com.yourrights.beans.UserResponse;
import com.yourrights.constants.Constants;
import com.yourrights.exceptions.UserException;
import com.yourrights.services.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
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
    
    @GetMapping(Constants.REGENERATE_PWD)
    public void signUp() {
	try {
	    userService.regeneratePasswords();
	} catch (MessagingException e) {
	   log.error("Error send email to regenerate password");
	   throw new UserException(Constants.ERROR, Constants.ERROR_SENDING_MAIL, "Error send email to regenerate password");
	}
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