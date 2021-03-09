package com.yww.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName TokenManager
 * @Descriprtion token管理类
 * @Author yww
 * @Date 2021/2/25 10:57
 * @Version 1.0
 **/
@Component
public class TokenManager {
    /**
     * Token的编码密钥
     */
    private final String tokenSignKey = "123456";

    /**
     * 使用JWT工具类根据用户名生成Token
     * @param username 用户名
     * @return Token
     */
    public String createToken(String username) {
        // Token的有效时长
        long tokenExpiration = 24 * 60 * 60 * 1000;
        return Jwts.builder().setSubject(username)
                // 设置有效时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                // 加密
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
    }

    /**
     * 根据Token中获取用户信息
     * @param token Token
     * @return 用户信息（Subject）
     */
    public String getUserFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * 删除Token
     * @param token Token
     */
    public void removeToken(String token) {}


}
