package com.yww.security.security;

import com.yww.commonutils.Md5Utils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @ClassName DefaultPasswordEncoder
 * @Descriprtion 密码的处理方法类型
 * @Author yww
 * @Date 2021/2/25 10:57
 * @Version 1.0
 **/
@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

    public DefaultPasswordEncoder() {
        this(-1);
    }

    /**
     * @param strength
     *            the log rounds to use, between 4 and 31
     */
    public DefaultPasswordEncoder(int strength) {

    }

    /**
     * 将未加密的密码进行MD5加密
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return Md5Utils.encrypt(rawPassword.toString());
    }

    /**
     * 把数据库中的密码与经过MD5加密的密码进行比较
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(Md5Utils.encrypt(rawPassword.toString()));
    }
}
