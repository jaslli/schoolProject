package com.yww.educenter.controller;


import com.yww.commonutils.JwtUtils;
import com.yww.commonutils.Result;
import com.yww.educenter.entity.UcenterMember;
import com.yww.educenter.entity.vo.RegisterVo;
import com.yww.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yww
 * @since 2021-02-18
 */
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService service;

    /**
     * 通过输入的信息来进行登陆
     * @param member 信息表单
     * @return 返回token
     * TODO 创建VO来对登陆信息进行标准化
     */
    @PostMapping("login")
    public Result loginUser(@RequestBody UcenterMember member) {
        String token = service.login(member);
        return Result.ok().data("token", token);
    }

    /**
     * 用户注册
     * @param registerVo 信息表单
     */
    @PostMapping("register")
    public Result registerUser(@RequestBody RegisterVo registerVo) {
        service.register(registerVo);
        return Result.ok();
    }

    /**
     * 根据请求信息中的token获取用户信息
     * @param request   请求
     * @return 用户信息
     */
    @GetMapping("getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request) {
        // 根据request获取用户ID
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        UcenterMember member = service.getById(memberId);

        return Result.ok().data("userInfo", member);

    }

}

