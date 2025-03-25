package com.example.task_v1.service;
import com.example.task_v1.RoleType;
import com.example.task_v1.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("не найден")))
                .map(user -> {
                    Collection<? extends GrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());
                    return new org.springframework.security.core.userdetails.User(
                            user.getUsername(), user.getPassword(), authorities);
                })
                .block();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<RoleType> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }
}
