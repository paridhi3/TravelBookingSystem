package com.travel.travelbookingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}password") // `{noop}` means no encoding
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(csrfConfig -> csrfConfig.disable()) // Disable CSRF for APIs
            .authorizeHttpRequests(authConfig -> authConfig
                .requestMatchers(HttpMethod.GET, "/api/**").permitAll() // ✅ Allow all GET requests
                .requestMatchers("/public/**").permitAll() // ✅ Allow unrestricted access to /public/**
                .anyRequest().authenticated() // 🔒 All other requests require authentication
            )
            .httpBasic(basicAuthConfig -> basicAuthConfig.realmName("TravelBookingSystem")) // ✅ Basic Auth
            .formLogin(formLoginConfig -> formLoginConfig.defaultSuccessUrl("/home", true)) // ✅ Enable form login with redirect
            .build();
    }
}
