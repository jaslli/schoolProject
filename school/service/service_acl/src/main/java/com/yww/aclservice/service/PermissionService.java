package com.yww.aclservice.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yww.aclservice.entity.Permission;

import java.util.List;

/**
 * @author yww
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 查询所有菜单
     * @return 菜单数据
     */
    List<Permission> queryAllMenu();

    /**
     * 根据菜单ID删除该菜单和其中的子菜单
     * @param id 菜单ID
     */
    void removeChildrenById(String id);

    /**
     * 根据角色ID来分配菜单
     * @param roleId 角色ID
     * @param permissionIds 菜单ID
     */
    void saveRolePermission(String roleId, String[] permissionIds);

    /**
     * 根据角色ID来获取菜单
     * @param roleId 角色ID
     * @return 菜单数据
     */
    List<Permission> selectAllMenu(String roleId);

    List<String> selectPermissionValueByUserId(String id);
    List<JSONObject> selectPermissionByUserId(String userId);
}
