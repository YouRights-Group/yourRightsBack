package com.yourrights;

import java.util.Properties;

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
	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	mailSender.setHost("smtp.gmail.com");
	mailSender.setPort(587);

	mailSender.setUsername("anaromanrodriguez1987@gmail.com");
	mailSender.setPassword("pdzgnvomtstzrprp");

	Properties props = mailSender.getJavaMailProperties();
	props.put("mail.transport.protocol", "smtp");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.debug", "true");

	return mailSender;
    }

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
