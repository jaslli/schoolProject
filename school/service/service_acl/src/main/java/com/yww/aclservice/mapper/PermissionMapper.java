package com.yww.aclservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yww.aclservice.entity.Permission;

import java.util.List;

/**
 * @author yww
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据用户ID来查询用户菜单
     * @param id 用户ID
     * @return List 用户菜单
     */
    List<String> selectPermissionValueByUserId(String id);

    /**
     * 查询所有的菜单数据
     * @return List 菜单数据
     */
    List<String> selectAllPermissionValue();

    /**
     * 根据用户ID查询菜单
     * @param userId 用户ID
     * @return 菜单
     */
    List<Permission> selectPermissionByUserId(String userId);

}
