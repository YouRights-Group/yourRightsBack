package com.yourrights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yourrights.filters.JWTAuthorizationFilter;

@SpringBootApplication
@EnableJpaRepositories("com.yourrights.repository")
public class YourRightsApplication {

    public static void main(String[] args) {
	SpringApplication.run(YourRightsApplication.class, args);
    }

    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()
		    .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
		    .authorizeRequests().antMatchers("/login", "/protests/list").permitAll()
		    .anyRequest().authenticated();
	}
    }

}
