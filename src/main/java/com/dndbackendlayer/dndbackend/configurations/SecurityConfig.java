
package com.dndbackendlayer.dndbackend.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Bean
    //authentication for users amd admins
    public UserDetailsService UserDetailsService(PasswordEncoder encoder) {
    // UserDetails admin= User.withUsername("loremasterinthor")
    //     .password(encoder.encode( "rastablasta1"))
    //     .roles("ADMIN")
    //     .build();
        
    // UserDetails user = User.withUsername("user31337")
    //     .password(encoder.encode("goblinking"))
    //     .roles("USER")
    //     .build();
    //     return new InMemoryUserDetailsManager(admin, user);
        return new UserInfoUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       return http.csrf().disable()
        .authorizeHttpRequests().requestMatchers("/users/**").authenticated()
        .and().formLogin()
        .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}