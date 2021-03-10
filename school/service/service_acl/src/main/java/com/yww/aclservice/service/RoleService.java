package com.yww.aclservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yww.aclservice.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @author yww
 */
public interface RoleService extends IService<Role> {
    /**
     * 根据用户ID获取角色信息
     * @param userId 用户ID
     * @return 角色信息
     */
    Map<String, Object> findRoleByUserId(String userId);

    /**
     * 根据用户ID和角色ID分配角色
     * @param userId 用户ID
     * @param roleIds 角色列表
     */
    void saveUserRole(String userId, String[] roleIds);

    /**
     * 根据用户id查询角色列表
     * @param id 用户id
     * @return 角色列表
     */
    public List<Role> selectRoleByUserId(String id);
}
