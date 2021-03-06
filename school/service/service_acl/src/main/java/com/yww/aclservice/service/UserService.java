package com.yww.aclservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yww.aclservice.entity.User;


public interface UserService extends IService<User> {
    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    User selectByUserName(String username);
}
