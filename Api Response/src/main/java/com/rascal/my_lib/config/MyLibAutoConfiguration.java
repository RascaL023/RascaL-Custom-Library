package com.rascal.my_lib.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import com.rascal.my_lib.exception.SecurityExceptionHandler;

import tools.jackson.databind.ObjectMapper;

@AutoConfiguration
public class MyLibAutoConfiguration {

    @Bean
    @ConditionalOnClass(name = "org.springframework.security.web.AuthenticationEntryPoint")
    public SecurityExceptionHandler securityExceptionHandler(ObjectMapper objectMapper) {
        return new SecurityExceptionHandler(objectMapper);
    }

}
