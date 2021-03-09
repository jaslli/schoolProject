package com.yww.security.filter;

import com.yww.commonutils.ResponseUtil;
import com.yww.commonutils.Result;
import com.yww.security.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName TokenAuthenticationFilter
 * @Descriprtion 授权过滤器
 * @Author yww
 * @Date 2021/2/25 10:56
 * @Version 1.0
 **/
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private final TokenManager tokenManager;
    private final RedisTemplate<String,Object> redisTemplate;

    /**
     * 全参构造器
     */
    public TokenAuthenticationFilter(AuthenticationManager authManager,
                                     TokenManager tokenManager,
                                     RedisTemplate<String,Object> redisTemplate) {
        super(authManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 授权过滤
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        logger.info("================="+req.getRequestURI());
        if (req.getRequestURI().indexOf("admin") == -1) {
            chain.doFilter(req, res);
            return;
        }
        // 获取当前认证成功的用户权限信息
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(req);
        } catch (Exception e) {
            ResponseUtil.out(res, Result.error());
        }

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            ResponseUtil.out(res, Result.error());
        }
        chain.doFilter(req, res);
    }

    /**
     * 获取权限列表
     * @param request 请求
     * @return 权限列表
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 获取头部的token
        String token = request.getHeader("token");
        if (token != null && !"".equals(token.trim())) {
            // 从Token中获取用户信息
            String userName = tokenManager.getUserFromToken(token);
            // 从Redis中获取权限列表
            List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);
            // 将权限列表转换成框架需要的集合列表
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            if (permissionValueList != null) {
                for(String permissionValue : permissionValueList) {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
                    authorities.add(authority);
                }
            }
            return new UsernamePasswordAuthenticationToken(userName, token, authorities);
        }
        return null;
    }
}
