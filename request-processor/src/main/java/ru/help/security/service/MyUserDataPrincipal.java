package ru.help.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.help.model.Authority;
import ru.help.model.User;


import java.util.*;

public class MyUserDataPrincipal implements UserDetails {

    private final User user;

    public MyUserDataPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> roles = user.getAuthorityList();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Authority role : roles) {authorities.add(new SimpleGrantedAuthority(role.getAuthority().name()));}
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
