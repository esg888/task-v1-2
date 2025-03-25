package com.example.task_v1.confiq;
import com.example.task_v1.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;


@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("password")) // Шифрованный
                .roles("USER")
                .build();
        UserDetails manager = User.builder()
                .username("manager")
                .password(encoder.encode("password"))
                .roles("MANAGER")
                .build();
        return new InMemoryUserDetailsManager(user, manager);
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/reg/**").permitAll()
//                        .requestMatchers("/error/**").permitAll()
//                        .requestMatchers("/api/users/**").hasAnyRole("USER", "MANAGER")
//                        .requestMatchers("/api/tasks/**").hasAnyRole("USER", "MANAGER")
//                        .requestMatchers(HttpMethod.POST, "/api/tasks/**").hasRole("MANAGER")
//                        .requestMatchers(HttpMethod.PUT, "/api/tasks/**").hasRole("MANAGER")
//                        .requestMatchers(HttpMethod.DELETE, "/api/tasks/**").hasRole("MANAGER")
//                        .anyRequest().authenticated()
//                )
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/api/reg/**")
//                        .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
//                )
//                .formLogin(Customizer.withDefaults())
//                .logout(Customizer.withDefaults());
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/reg/**").permitAll()
                        .requestMatchers("/error/**").permitAll()
                        .requestMatchers("/api/users/**").hasAnyRole("USER", "MANAGER")
                        .requestMatchers("/api/tasks/**").hasAnyRole("USER", "MANAGER")
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/reg/**")
                )
                .httpBasic(Customizer.withDefaults()) // Basic Auth
                .formLogin(form -> form.disable()); // Отк. форму лог.

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
