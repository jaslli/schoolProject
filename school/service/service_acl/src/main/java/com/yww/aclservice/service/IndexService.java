package com.yww.aclservice.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface IndexService {
    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    Map<String, Object> getUserInfo(String username);

    /**
     * 根据用户名查询角色
     * @param username 用户名
     * @return 角色信息
     */
    List<JSONObject> getMenu(String username);
}
