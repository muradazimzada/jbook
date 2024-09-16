package edu.jhu.bookservice.service;

import edu.jhu.bookservice.dto.UserDto;
import edu.jhu.bookservice.proxy.UserServiceProxy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceProxy userServiceProxy;

    public UserDetailsServiceImpl(UserServiceProxy userServiceProxy) {
        this.userServiceProxy = userServiceProxy;
    }

    @Override
    // email is token
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        UserDto user = userServiceProxy.getUserDetails(token);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                "Password",
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_USER") // Assuming "ROLE_USER" is the role you want to assign
                )// This is the encoded password from the database
                 // Assuming `getAuthorities()` returns the roles/authorities
        );
    }
}
