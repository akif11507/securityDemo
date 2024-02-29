package com.example.securitydemo.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(new HttpSessionCsrfTokenRepository()).and()
                .authorizeRequests()
//                .antMatchers("/h2-console/**").permitAll()

                .antMatchers("/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/h2-console/**",
                        "/**"
                )
                .permitAll()
                .anyRequest().authenticated();
    }


}

