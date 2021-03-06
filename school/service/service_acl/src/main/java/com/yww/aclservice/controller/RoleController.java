package com.yww.aclservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yww.aclservice.entity.Role;
import com.yww.aclservice.service.RoleService;
import com.yww.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName RoleController
 * @Descriprtion 角色管理
 * @Author yww
 * @Date 2021/2/25 11:45
 * @Version 1.0
 **/
@Api(tags= "角色管理")
@RestController
@RequestMapping("/admin/acl/role")
public class RoleController {
    @Autowired
    private RoleService service;

    @ApiOperation("获取用户分页列表数据")
    @GetMapping("{current}/{limit}")
    public Result getList(
            @ApiParam(name = "current",value = "当前页数",required = true)
            @PathVariable("current") Long current,
            @ApiParam(name = "limit",value = "总页数",required = true)
            @PathVariable("limit") Long limit,
            @ApiParam(name = "roleVo",value = "查询条件",required = true)
            Role roleVo) {
        Page<Role> page = new Page<>(current, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(roleVo.getRoleName())) {
            wrapper.like("role_name", roleVo.getRoleName());
        }
        service.page(page, wrapper);
        return Result.ok().data("items", page.getRecords()).data("total", page.getTotal());
    }

    @ApiOperation("根据角色ID获取角色信息")
    @GetMapping("get/{roleId}")
    public Result getInfo(@PathVariable("roleId") String roleId) {
        Role role = service.getById(roleId);
        return Result.ok().data("item", role);
    }

    @ApiOperation("添加角色")
    @PostMapping("save")
    public Result saveRole(@RequestBody Role role) {
        service.save(role);
        return Result.ok();
    }

    @ApiOperation("修改角色信息")
    @PutMapping("update")
    public Result update(@RequestBody Role role) {
        service.updateById(role);
        return Result.ok();
    }

    @ApiOperation("删除角色信息")
    @DeleteMapping("remove/{roleId}")
    public Result remove(@PathVariable("roleId") String roleId) {
        service.removeById(roleId);
        return Result.ok();
    }

    @ApiOperation("根据ID列表删除角色")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        service.removeByIds(idList);
        return Result.ok();
    }
}
