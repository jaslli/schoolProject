package com.yww.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yww.aclservice.entity.Permission;
import com.yww.aclservice.entity.RolePermission;
import com.yww.aclservice.entity.User;
import com.yww.aclservice.helper.MemuHelper;
import com.yww.aclservice.helper.PermissionHelper;
import com.yww.aclservice.mapper.PermissionMapper;
import com.yww.aclservice.service.PermissionService;
import com.yww.aclservice.service.RolePermissionService;
import com.yww.aclservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PermissionServiceImpl
 * @Descriprtion TODO
 * @Author yww
 * @Date 2021/2/25 11:40
 * @Version 1.0
 **/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserService userService;

    /**
     * @Descriprtion 查询所有菜单
     * @return 菜单
     */
    @Override
    public List<Permission> queryAllMenu() {

        // 查询菜单表中所有数据
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> list = baseMapper.selectList(wrapper);

        // 封装返回的数据，层级显示
        return buildPermission(list);
    }

    /**
     * @Descriprtion 设置顶层菜单的level为1
     * @param permissionList 所有菜单数据
     * @return 封装后的数据
     */
    public static List<Permission> buildPermission(List<Permission> permissionList) {

        List<Permission> result = new ArrayList<>();

        for (Permission permission : permissionList) {
            // 获取顶层菜单（pid=0）
            if ("0".equals(permission.getPid())) {
                // 设置顶层菜单的level为1，表示为第一级菜单
                permission.setLevel(1);
                // 将顶层菜单加入到结果列表（该顶层菜单包括了子菜单）
                result.add(selectChildren(permissionList,permission));
            }
        }

        return result;
    }

    /**
     * @Descriprtion 根据一个上级菜单，设置其子菜单的level，并进行数据封装
     * @param permissionList 所有菜单数据
     * @param permission 一个上级菜单
     * @return 将子菜单封装好的上级菜单
     */
    public static Permission selectChildren(List<Permission> permissionList, Permission permission) {

        if (permission.getChildren() == null) {
            permission.setChildren(new ArrayList<>());
        }

        for (Permission node : permissionList) {
            // 判断该菜单是否是上一层菜单的子菜单
            if (permission.getId().equals(node.getPid())) {
                node.setLevel(permission.getLevel() + 1);
                permission.getChildren().add(selectChildren(permissionList,node));
            }
        }
        return permission;
    }


    /**
     * @Descriprtion 根据菜单的ID，递归删除菜单
     * @param id 菜单ID
     */
    @Override
    public void removeChildrenById(String id) {
        // 存储菜单ID和其所有子菜单的ID
        List<String> idList = new ArrayList<>();
        idList.add(id);
        this.selectChildrenById(id, idList);
        baseMapper.deleteBatchIds(idList);
    }

    /**
     * @Descriprtion 根据上级菜单ID来查询所有其子菜单ID
     * @param id 父级ID
     * @param idList 需要删除的ID列表
     */
    private void selectChildrenById(String id,List<String> idList) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", id);
        wrapper.select("id");
        List<Permission> childIdList = baseMapper.selectList(wrapper);
        // 递归每个子菜单
        childIdList.forEach(item -> {
            idList.add(item.getId());
            this.selectChildrenById(item.getId(),idList);
        });
    }

    /**
     * @Descriprtion 给角色分配权限
     * @param roleId 角色ID
     * @param permissionIds 菜单的ID列表
     */
    @Override
    public void saveRolePermission(String roleId, String[] permissionIds) {

        List<RolePermission> rolePermissionList = new ArrayList<>();

        for (String permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }

        rolePermissionService.saveBatch(rolePermissionList);
    }


    /**
     * 根据用户id获取用户菜单
     * @param id 用户ID
     * @return 用户菜单
     */
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        List<Permission> permissionList = PermissionHelper.build(selectPermissionList);
        return MemuHelper.build(permissionList);
    }

    /**
     * @Descriprtion 查询角色获取的菜单
     * @param roleId 角色ID
     * @return 角色的权限菜单
     */
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        // 查询所有角色
        List<Permission> permissionList =
                baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));
        // 根据角色ID查询所有菜单数据
        List<RolePermission> rolePermissionList =
                rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id", roleId));

        for (Permission permission : permissionList) {
            for(RolePermission rolePermission : rolePermissionList) {
                if (rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }
        // 树形显示的返回菜单数据
        return buildPermission(permissionList);
    }

    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if (null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     *	递归获取子节点
     * @param id
     * @param idList
     */
    private void selectChildListById(String id, List<String> idList) {
        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }

    /**
     * 使用递归方法建菜单
     * @param treeNodes
     * @return
     */
    private static List<Permission> bulid(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    private static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<Permission>());

        for (Permission it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }



}
