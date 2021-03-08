package com.yww.security.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName SecurityUser
 * @Descriprtion 安全认证用户详情信息
 * @Author yww
 * @Date 2021/2/25 10:54
 * @Version 1.0
 **/
@Data
@Slf4j
public class SecurityUser implements UserDetails {

    /**
     * 当前登录用户
     */
    private transient User currentUserInfo;

    /**
     * 当前权限
     */
    private List<String> permissionValueList;

    public SecurityUser() { }

    public SecurityUser(User user) {
        if (user != null) {
            this.currentUserInfo = user;
        }
    }

    /**
     * 获取用户权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(String permissionValue : permissionValueList) {
            if(StringUtils.isEmpty(permissionValue)) {
                continue;
            }
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
            authorities.add(authority);
        }

        return authorities;
    }

    /**
     * 获取用户密码
     */
    @Override
    public String getPassword() {
        return currentUserInfo.getPassword();
    }

    /**
     * 获取用户名
     */
    @Override
    public String getUsername() {
        return currentUserInfo.getUsername();
    }

    /**
     * 判断用户登陆是否过期
     * @return true表示过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 判断用户是否被锁定
     * @return true表示锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 判断用户凭证是否
     * @return true表示未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     *  判断用户是否可用
     * @return true表示可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
