package com.yww.security.config;

import com.yww.security.filter.TokenAuthenticationFilter;
import com.yww.security.filter.TokenLoginFilter;
import com.yww.security.security.DefaultPasswordEncoder;
import com.yww.security.security.TokenLogoutHandler;
import com.yww.security.security.TokenManager;
import com.yww.security.security.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @ClassName TokenWebSecurityConfig
 * @Descriprtion SpringSecurity的核心配置类
 * @Author yww
 * @Date 2021/2/25 10:53
 * @Version 1.0
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final TokenManager tokenManager;
    private final DefaultPasswordEncoder defaultPasswordEncoder;
    private final RedisTemplate<String,Object> redisTemplate;

    /**
     * 全参构造器
     */
    @Autowired
    public TokenWebSecurityConfig(UserDetailsService userDetailsService,
                                  DefaultPasswordEncoder defaultPasswordEncoder,
                                  TokenManager tokenManager,
                                  RedisTemplate<String,Object> redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 核心配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedEntryPoint())
                .and().csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin().loginProcessingUrl("/admin/acl/login")
                // 退出路径
                .and().logout().logoutUrl("/admin/acl/index/logout")
                // 设置自定义的退出登陆
                .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate)).and()
                // 传入自定义认证过滤器
                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
                // 传入自定义授权过滤器
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();
    }

    /**
     * 密码处理
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    /**
     * 配置不拦截的请求
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/api/**",
                // swagger-bootstrap的权限
                "/doc.html/**",
                "/webjars/**",
                "/swagger-resources",
                "/v2/**");
    }
}
