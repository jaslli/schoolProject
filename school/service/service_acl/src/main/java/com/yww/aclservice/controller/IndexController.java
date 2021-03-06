package com.yww.aclservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.yww.aclservice.service.IndexService;
import com.yww.commonutils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName IndexController
 * @Descriprtion 显示菜单接口
 * @Author yww
 * @Date 2021/2/25 11:45
 * @Version 1.0
 **/
@RestController
@RequestMapping("/admin/acl/index")
public class IndexController {

    @Autowired
    private IndexService service;

    @ApiOperation("根据Token获得用户信息")
    @GetMapping("info")
    public Result info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = service.getUserInfo(username);
        return Result.ok().data(userInfo);
    }

    @ApiOperation("根据用户名获取菜单")
    @GetMapping("menu")
    public Result getMenu() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = service.getMenu(username);
        return Result.ok().data("permissionList", permissionList);
    }

    @ApiOperation("退出登陆")
    @PostMapping("logout")
    public Result logout() {
        return Result.ok();
    }


}
