package com.yourrights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yourrights.constants.Constants;
import com.yourrights.filters.JWTAuthorizationFilter;

@SpringBootApplication
//@EnableJpaRepositories("com.yourrights.repository")
public class YourRightsApplication {

    public static void main(String[] args) {
	SpringApplication.run(YourRightsApplication.class, args);
    }

    @Bean
    public JWTAuthorizationFilter getFilterBean() {
	return new JWTAuthorizationFilter();
    }

    @Bean
    public JavaMailSender getMailServer() {
	return new JavaMailSenderImpl();
    }

//    @Bean
//    public ConfigProperties getConfigProperties() {
//	return new ConfigProperties();
//    }

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

}
