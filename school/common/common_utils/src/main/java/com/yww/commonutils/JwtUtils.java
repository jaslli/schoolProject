package com.yww.commonutils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @ClassName JwtUtils
 * @Descriprtion Jwt工具类
 * @Author yww
 * @Date 2021/2/18 16:52
 * @Version 1.0
 **/
public class JwtUtils {

    /**
     * Token的过期时间
     */
    public static final long EXPIRE = 1000 * 60 * 60 *24;
    /**
     * TOKEN的加密密钥
     */
    public static final String APP_SECPET = "Z7evdo7Hc2UPHx9ttuRaomyawhyoNl";

    /**
     * 根据用户信息获取Token
     * @param id 用户ID
     * @param nickname 用户签名
     * @return Token字符串
     */
    public static String getJwtToken(String id,String nickname) {
        return Jwts.builder()
                //  设置JWT的头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //  设置Token的过期时间
                .setSubject("school_token")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //  设置Token的主体部分，存储用户信息
                .claim("id",id)
                .claim("nickname",nickname)
                //  生成加密字符串
                .signWith(SignatureAlgorithm.HS256,APP_SECPET)
                .compact();
   }

    /**
     * 判断Token是否存在或者是否有效
     * @param jwtToken Tokrn
     * @return true表示存在且有效
     */
   public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(APP_SECPET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
   }

    /**
     * 判断Token是否存在或者是否有效
     * @param request 请求
     * @return true表示存在且有效
     */
   public static boolean checkToken(HttpServletRequest request) {
       try {
           // 通过request获取token
           String jwtToken = request.getHeader("token");
           if(StringUtils.isEmpty(jwtToken)) {
               return false;
           }
           Jwts.parser().setSigningKey(APP_SECPET).parseClaimsJws(jwtToken);
       } catch (Exception e) {
           e.printStackTrace();
           return false;
       }
       return true;
   }

    /**
     * 根据Token字符串获取用户信息
     * @param request 请求
     * @return 用户信息
     */
   public static String getMemberIdByJwtToken(HttpServletRequest request) {
       String jwtToken = request.getHeader("token");
       if (StringUtils.isEmpty(jwtToken)) {
           return "";
       }
       Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECPET).parseClaimsJws(jwtToken);
       Claims claims = claimsJws.getBody();
       return (String) claims.get("id");
   }

}
