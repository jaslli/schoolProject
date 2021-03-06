package com.yww.aclservice.service.impl;

import com.yww.aclservice.entity.User;
import com.yww.aclservice.service.PermissionService;
import com.yww.aclservice.service.UserService;
import com.yww.security.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserDetailsServiceImpl
 * @Descriprtion 认证用户的实现类
 * @Author yww
 * @Date 2021/2/25 11:40
 * @Version 1.0
 **/
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @throws UsernameNotFoundException 找不到该用户
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名查询用户数据
        User user = userService.selectByUserName(username);

        // 判断用户是否存在
        if (null == user){
            throw new UsernameNotFoundException("用户名不存在！");
        }

        // 返回UserDetails实现类
        com.yww.security.entity.User curUser = new com.yww.security.entity.User();
        BeanUtils.copyProperties(user,curUser);
        // 根据用户信息查询用户权限列表
        List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(curUser);
        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }

}
