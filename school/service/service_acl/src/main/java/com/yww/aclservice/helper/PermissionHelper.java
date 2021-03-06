package com.yww.aclservice.helper;

import com.yww.aclservice.entity.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PermissionHelper
 * @Descriprtion 根据权限构建菜单数据
 * @Author yww
 * @Date 2021/3/7 1:32
 * @Version 1.0
 **/
public class PermissionHelper {
    /**
     * 使用递归方法建菜单
     */
    public static List<Permission> build(List<Permission> treeNodes) {
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
     */
    public static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
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
