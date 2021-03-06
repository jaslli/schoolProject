package com.yww.aclservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yww.aclservice.entity.User;
import com.yww.aclservice.service.RoleService;
import com.yww.aclservice.service.UserService;
import com.yww.commonutils.Md5Utils;
import com.yww.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName UserController
 * @Descriprtion 用户管理
 * @Author yww
 * @Date 2021/2/25 11:45
 * @Version 1.0
 **/
@Api(tags= "用户管理")
@RestController
@RequestMapping("/admin/acl/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private RoleService roleService;

    @ApiOperation("获取管理用户的分页列表")
    @GetMapping("{current}/{limit}")
    public Result getList(
            @ApiParam(name = "current",value = "当前页数",required = true)
            @PathVariable("current") Long current,
            @ApiParam(name = "limit",value = "总页数",required = true)
            @PathVariable("limit") Long limit,
            @ApiParam(name = "userVo",value = "查询条件",required = true)
            User userVo) {
        Page<User> page = new Page<>(current, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userVo.getUsername())) {
            wrapper.like("username", userVo.getUsername());
        }
        service.page(page, wrapper);
        long total = page.getTotal();
        List<User> recodes = page.getRecords();
        return Result.ok().data("items", recodes).data("total", total);
    }

    @ApiOperation("新增管理用户")
    @PostMapping("save")
    public Result saveUser(@RequestBody User user) {
        user.setPassword(Md5Utils.encrypt(user.getPassword()));
        service.save(user);
        return Result.ok();
    }

    @ApiOperation("修改管理用户数据")
    @PutMapping("update")
    public Result update(@RequestBody User user) {
        service.updateById(user);
        return Result.ok();
    }

    @ApiOperation("删除管理用户")
    @DeleteMapping("remove/{userId}")
    public Result remove(@PathVariable("userId") String userId) {
        service.removeById(userId);
        return Result.ok();
    }

    @ApiOperation("批量删除管理用户")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        service.removeByIds(idList);
        return Result.ok();
    }

    @ApiOperation("根据用户ID获取角色数据")
    @GetMapping("toAssign/{userId}")
    public Result getRole(@PathVariable("userId") String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return Result.ok().data(roleMap);
    }

    @ApiOperation("根据用户ID分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestParam String userId,@RequestParam String[] roleIds) {
        roleService.saveUserRole(userId, roleIds);
        return Result.ok();
    }
}
