package com.hexaware.automobile.services;

import com.hexaware.automobile.entities.User;
import com.hexaware.automobile.entities.Officer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Long userId;

    private CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.userId = userId;
    }

    public static CustomUserDetails fromUser(User user) {
        return new CustomUserDetails(
            user.getEmail(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())),
            user.getId()
        );
    }

    public static CustomUserDetails fromOfficer(Officer officer) {
        return new CustomUserDetails(
            officer.getEmail(),
            officer.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(officer.getRole().name())),
            officer.getId()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}