package com.example.task_v1.confiq;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    @Bean
      public GrantedAuthoritiesMapper authoritiesMapper() {
        return authorities -> authorities.stream()
                .map(a -> new SimpleGrantedAuthority(
                        a.getAuthority().startsWith("ROLE_") ? a.getAuthority() : "ROLE_" + a.getAuthority()
                ))
                .collect(Collectors.toList());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/reg/**").permitAll()
                        .requestMatchers("/error/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/tasks/**").hasAnyRole("USER", "MANAGER") // GET - getAllTasks, getTaskById
                        .requestMatchers(HttpMethod.POST, "/api/tasks/**").hasRole("MANAGER")   // POST - create
                        .requestMatchers(HttpMethod.PUT, "/api/tasks/**").hasRole("MANAGER")    // PUT - update
                        .requestMatchers(HttpMethod.PATCH, "/api/tasks/**").hasAnyRole("USER", "MANAGER") // PATCH - addObserverToTask
                        .requestMatchers(HttpMethod.DELETE, "/api/tasks/**").hasRole("MANAGER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()); // чем заменить?
        return http.build();
    }
}
