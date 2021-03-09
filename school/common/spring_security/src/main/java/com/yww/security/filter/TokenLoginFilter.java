package com.yww.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yww.commonutils.ResponseUtil;
import com.yww.commonutils.Result;
import com.yww.security.entity.SecurityUser;
import com.yww.security.entity.User;
import com.yww.security.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @ClassName TokenLoginFilter
 * @Descriprtion 认证过滤器
 * @Author yww
 * @Date 2021/2/25 10:55
 * @Version 1.0
 **/
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;
    private final RedisTemplate<String,Object> redisTemplate;

    /**
     * 全参数构造器
     */
    public TokenLoginFilter(AuthenticationManager authenticationManager,
                            TokenManager tokenManager,
                            RedisTemplate<String,Object> redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        // 设置登陆地址，拦截用户信息
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login","POST"));
    }

    /**
     * 根据用户名和密码进行身份验证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            // 获取表单提交的数据
            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
            // 交给SpringSecurity管理
            // 1. 用户名   2. 密码   3. 权限
            return authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 认证成功后调用的方法
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {
        // 获取成功认证后的用户信息
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        // 生成Token
        String token = tokenManager.createToken(user.getCurrentUserInfo().getUsername());
        // 将用户名为键，权限列表为值放入Redis缓存
        redisTemplate.opsForValue().set(user.getCurrentUserInfo().getUsername(), user.getPermissionValueList());

        ResponseUtil.out(res, Result.ok().data("token", token));
    }

    /**
     * 认证失败后调用的方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException e) {
        ResponseUtil.out(response, Result.error());
    }

}
