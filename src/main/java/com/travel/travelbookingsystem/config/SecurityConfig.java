//package com.travel.travelbookingsystem.config;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()  // Disable CSRF (for testing only)
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll()  // ✅ Allow all requests without authentication
//                );
//
//        return http.build();
//    }
//}

package com.travel.travelbookingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.travel.travelbookingsystem.service.PassengerService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PassengerService passengerService;

    public SecurityConfig(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        // AuthenticationManager can now be configured directly
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(passengerService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .authorizeHttpRequests(registry -> {
//                    registry.requestMatchers("/req/login").authenticated();
                	registry.requestMatchers("/req/login").permitAll();// Require authentication ONLY for login through a web page (will not work for postman)
                    registry.anyRequest().permitAll(); // Allow all other requests without authentication
                })
                .formLogin(login -> 
                    login.loginPage("/req/login").permitAll() // Use login page for authentication
                )
                .logout(logout -> logout
                    .logoutUrl("/req/logout")
                    .permitAll()
                    .logoutSuccessUrl("/req/login?logout")
                )
                .build();
    }
    
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf().disable()
//                .authorizeHttpRequests(registry -> {
//                    registry.requestMatchers("/api/passengers/login").permitAll(); // Allow login API from postman
//                    registry.anyRequest().authenticated(); // Secure other endpoints
//                })
//                .formLogin(login -> 
//                    login.loginProcessingUrl("/api/passengers/login") // Use login API instead of a page
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                    .logoutUrl("/api/passengers/logout")
//                    .permitAll()
//                )
//                .build();
//    }


}



