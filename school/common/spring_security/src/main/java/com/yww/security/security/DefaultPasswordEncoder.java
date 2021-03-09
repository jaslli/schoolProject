package com.yww.security.security;

import com.yww.commonutils.Md5Utils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @ClassName DefaultPasswordEncoder
 * @Descriprtion 密码的处理工具类
 * @Author yww
 * @Date 2021/2/25 10:57
 * @Version 1.0
 **/
@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

    public DefaultPasswordEncoder() {
        this(-1);
    }

    public DefaultPasswordEncoder(int strength) { }

    /**
     * 将未加密的密码进行MD5加密
     * @param rawPassword 传入的密码
     * @return String MD5加密后的密码
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return Md5Utils.encrypt(rawPassword.toString());
    }

    /**
     * 将数据库中的密码与经过MD5加密的密码进行比较
     * @param rawPassword 传入的密码
     * @param encodedPassword 加密的密码
     * @return boolean true表示密码一样
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(Md5Utils.encrypt(rawPassword.toString()));
    }
}
