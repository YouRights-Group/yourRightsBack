package com.yourrights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.yourrights.filters.JWTAuthorizationFilter;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({ "com.yourrights" })
public class YourRightsApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
	SpringApplication.run(YourRightsApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(YourRightsApplication.class);
    }

    @Bean
    public JWTAuthorizationFilter getFilterBean() {
    	return new JWTAuthorizationFilter();
    }

}
