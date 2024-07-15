package com.restapis.expensetracker.model.user_info;

import com.restapis.expensetracker.entity.Role;
import com.restapis.expensetracker.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
public class UserInfoUserDetails implements UserDetails {
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserInfoUserDetails(UserInfo userInfo) {
        this.email = userInfo.getEmail();
        this.password = userInfo.getPassword();
        this.authorities = mapPermissionsToAuthorities(userInfo.getRoles());
    }

    private Collection<? extends GrantedAuthority> mapPermissionsToAuthorities(Set<Role> roleSet) {
        return roleSet.stream()
                .map(role -> role.getPermissions())
                .flatMap(Collection::stream)
                .map(permission -> {
                    System.out.println(permission);
                    return new SimpleGrantedAuthority(permission.getName());
                })
                .collect(Collectors.toList());
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
        return email;
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
