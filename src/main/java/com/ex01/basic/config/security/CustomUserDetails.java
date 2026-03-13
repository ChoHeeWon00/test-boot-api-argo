package com.ex01.basic.config.security;

import lombok.ToString;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@ToString
public class CustomUserDetails implements UserDetails {
    private int id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(int id, String username, String password,
                             Collection<? extends GrantedAuthority> authorities){
        this.id = id; this.username = username;
        this.password = password; this.authorities = authorities;
    }

    public int getId(){
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public @Nullable String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
}
