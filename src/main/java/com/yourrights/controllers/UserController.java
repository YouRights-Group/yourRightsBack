package com.yourrights.controllers;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yourrights.beans.RegeneratePWDBean;
import com.yourrights.beans.User;
import com.yourrights.beans.UserResponse;
import com.yourrights.constants.Constants;
import com.yourrights.exceptions.UserException;
import com.yourrights.services.UserService;

import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(Constants.LOGIN)
	public UserResponse login(@RequestBody User user) {

		String token = userService.getJWTToken(user.getEmail());

		userService.login(user, token);

		UserResponse response = new UserResponse();
		response.setToken(token);
		return response;

	}

	@PostMapping(Constants.SIGN_UP)
	public void signUp(@RequestBody User user) {
		userService.saveUser(user);
	}

	@PostMapping(Constants.FORGOT_PWD)
	public String forgotPassword(User user) {
		try {
			return userService.forgotPassword(user.getEmail());

		} catch (MessagingException e) {
			log.error("Error send email to regenerate password");
			throw new UserException(Constants.ERROR, Constants.ERROR_SENDING_MAIL,
					"Error send email to regenerate password");
		}
	}

	@PatchMapping(Constants.REGENERATE_PWD)
	public void regeneratePassword(@RequestBody RegeneratePWDBean regeneratePwdBean) {
		userService.regeneratePassword(regeneratePwdBean);
	}

	@GetMapping(Constants.RESET_TOKEN)
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public String resetToken() {
		return userService.resetToken();
	}

}