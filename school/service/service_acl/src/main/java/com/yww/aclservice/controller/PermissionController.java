package com.yww.aclservice.controller;

import com.yww.aclservice.entity.Permission;
import com.yww.aclservice.service.PermissionService;
import com.yww.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName PermissionController
 * @Descriprtion 菜单管理
 * @Author yww
 * @Date 2021/2/25 11:45
 * @Version 1.0
 **/
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {

    @Autowired
    private PermissionService service;

    @ApiOperation("查询所有菜单")
    @GetMapping
    public Result indexAllPermission() {
        List<Permission> list = service.queryAllMenu();
        return Result.ok().success(true).data("children", list);
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        service.removeChildrenById(id);
        return Result.ok();
    }

    @ApiOperation("给角色分配权限")
    @PostMapping("doAssign")
    public Result doAssign(String roleId, String[] permissionIds) {
        service.saveRolePermission(roleId, permissionIds);
        return Result.ok();
    }

    @ApiOperation("根据角色id获取菜单")
    @GetMapping("toAssign/{roleId}")
    public Result getMenuById(@PathVariable("roleId") String roleId) {
        List<Permission> list = service.selectAllMenu(roleId);
        return Result.ok().data("children", list);
    }

    @ApiOperation("新增菜单")
    @PostMapping("save")
    public Result saveMenu(@RequestBody Permission permission) {
        service.save(permission);
        return Result.ok();
    }

    @ApiOperation("修改菜单")
    @PutMapping("update")
    public Result updateById(@RequestBody Permission permission) {
        service.updateById(permission);
        return Result.ok();
    }

}
