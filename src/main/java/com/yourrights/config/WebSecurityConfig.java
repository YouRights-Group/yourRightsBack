package com.yourrights.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yourrights.constants.Constants;
import com.yourrights.filters.JWTAuthorizationFilter;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JWTAuthorizationFilter filter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
	    http.csrf().disable().addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class).authorizeRequests()
		    .antMatchers(HttpMethod.POST).permitAll().antMatchers(HttpMethod.GET).permitAll()
		    .antMatchers(Constants.LOGIN, Constants.REGENERATE_PWD, Constants.FORGOT_PWD, "/v2/api-docs",
			    "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html",
			    "/webjars/**")
		    .permitAll().anyRequest().authenticated();
	}
}