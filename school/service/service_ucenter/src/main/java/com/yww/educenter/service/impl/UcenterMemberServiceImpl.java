package com.yww.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yww.commonutils.JwtUtils;
import com.yww.commonutils.Md5Utils;
import com.yww.educenter.entity.UcenterMember;
import com.yww.educenter.entity.vo.RegisterVo;
import com.yww.educenter.mapper.UcenterMemberMapper;
import com.yww.educenter.service.UcenterMemberService;
import com.yww.servicebase.handler.exceptionHandler.schoolException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author yww
 * @since 2021-02-18
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    /**
     * 登陆
     * @param member
     * @return token
     */
    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();

        // 校验参数是否合法
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new schoolException(20001, "输入信息错误");
        }
        // 判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if (ucenterMember == null) {
            throw new schoolException(20001, "手机号不存在");
        }
        // 判断密码
        if (!Md5Utils.encrypt(password).equals(ucenterMember.getPassword())) {
            throw new schoolException(20001, "密码错误");
        }
        // 判断用户是否被禁用
        if (ucenterMember.getIsDisabled()) {
            throw new schoolException(20001, "用户被禁用");
        }
        // 登陆成功
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return token;
    }

    /**
     * 注册
     * @param registerVo 注册表单
     */
    @Override
    public void register(RegisterVo registerVo) {

        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname)) {
            throw new schoolException(20001, "信息不全，注册失败");
        }

/*          判断验证码
        String code = registerVo.getCode();
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode) {
            throw new schoolException(20001, "验证码不正确");
        }
 */

        // 判断手机号是否存在
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new schoolException(20001, "手机号已经存在");
        }

        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setPassword(Md5Utils.encrypt(password));
        member.setNickname(nickname);
        member.setIsDisabled(false);
        member.setAvatar("https://img.yww52.com/avatar.jpg");

        baseMapper.insert(member);
    }
}
