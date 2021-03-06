package com.yww.aclservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yww.aclservice.entity.User;
import com.yww.aclservice.mapper.UserMapper;
import com.yww.aclservice.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Descriprtion TODO
 * @Author yww
 * @Date 2021/2/25 11:40
 * @Version 1.0
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public com.yww.aclservice.entity.User selectByUserName(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username",username));
    }
}
