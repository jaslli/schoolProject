package com.yww.security.security;


import com.yww.commonutils.ResponseUtil;
import com.yww.commonutils.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName TokenLogoutHandler
 * @Descriprtion 退出登陆处理器
 * @Author yww
 * @Date 2021/2/25 10:58
 * @Version 1.0
 **/
public class TokenLogoutHandler implements LogoutHandler {
    private final TokenManager tokenManager;
    private final RedisTemplate<String,Object> redisTemplate;

    /**
     * 全参数构造器
     * @param tokenManager TokenManager管理类
     * @param redisTemplate RedisTemplate模板
     */
    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate<String,Object> redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 登出
     * @param request 请求
     * @param response  响应
     * @param authentication 权限
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 从请求头部获取Token
        String token = request.getHeader("token");
        if (token != null) {
            // 删除Token
            tokenManager.removeToken(token);
            // 删除Redis缓存中对该用户名的缓存信息
            String userName = tokenManager.getUserFromToken(token);
            redisTemplate.delete(userName);
        }
        ResponseUtil.out(response, Result.ok());
    }

}
