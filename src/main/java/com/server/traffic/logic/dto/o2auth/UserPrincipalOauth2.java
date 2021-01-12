package com.server.traffic.logic.dto.o2auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.traffic.logic.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipalOauth2 implements UserDetails {

    private String username;

    @JsonIgnore
    private String password;

    private String fullName;
    private Long userId;
    private String email;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipalOauth2(String username, String password , String fullName , String  email, Long userId , Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.userId = userId;
        this.email = email;
        this.authorities = authorities;
    }

    public static UserPrincipalOauth2 createPrincipalOauth2(UserEntity userEntity){
        List<GrantedAuthority> authorities = userEntity.getRoles().stream().map(roleEntity ->
                new SimpleGrantedAuthority(roleEntity.getCode())
        ).collect(Collectors.toList());
        return new UserPrincipalOauth2(
                userEntity.getUserName(),
                userEntity.getPassWord(),
                userEntity.getFullName(),
                userEntity.getEmail(),
                userEntity.getId(),
                authorities
        );
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
