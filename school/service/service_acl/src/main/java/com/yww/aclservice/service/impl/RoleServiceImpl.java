package com.yww.aclservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yww.aclservice.entity.Role;
import com.yww.aclservice.entity.UserRole;
import com.yww.aclservice.mapper.RoleMapper;
import com.yww.aclservice.service.RoleService;
import com.yww.aclservice.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName RoleServiceImpl
 * @Descriprtion TODO
 * @Author yww
 * @Date 2021/2/25 11:40
 * @Version 1.0
 **/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 根据用户ID获取角色信息
     * @param userId 用户ID
     * @return 角色信息
     */
    @Override
    public Map<String, Object> findRoleByUserId(String userId) {

        List<Role> roleList = baseMapper.selectList(null);
        List<UserRole> userRoleList =
                userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", userId).select("role_id"));

        List<String> list = userRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());

        List<Role> assignRoles = new ArrayList<>();
        for (Role role : roleList) {
            if (list.contains(role.getId())) {
                assignRoles.add(role);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignRoles);
        roleMap.put("roleList", roleList);
        return roleMap;
    }

    /**
     * 根据用户ID和角色ID分配角色
     * @param userId 用户ID
     * @param roleIds 角色列表
     */
    @Override
    public void saveUserRole(String userId, String[] roleIds) {
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));

        List<UserRole> userRoleList = new ArrayList<>();
        for (String roleId : roleIds) {
            if (StringUtils.isEmpty(roleId)) {
                continue;
            }
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleList.add(userRole);
        }
        userRoleService.saveBatch(userRoleList);
    }

    /**
     * 根据用户id查询角色列表
     * @param id 用户id
     * @return 角色列表
     */
    @Override
    public List<Role> selectRoleByUserId(String id) {
        //根据用户id拥有的角色id
        List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", id).select("role_id"));
        List<String> roleIdList = userRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        List<Role> roleList = new ArrayList<>();
        if(roleIdList.size() > 0) {
            roleList = baseMapper.selectBatchIds(roleIdList);
        }
        return roleList;
    }


}
