package com.yww.security.security;


import com.yww.commonutils.ResponseUtil;
import com.yww.commonutils.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName UnauthorizedEntryPoint
 * @Descriprtion 未授权的统一处理方式
 * @Author yww
 * @Date 2021/2/25 10:58
 * @Version 1.0
 **/
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    /**
     *  未授权的统一处理
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        ResponseUtil.out(response, Result.error());
    }
}
