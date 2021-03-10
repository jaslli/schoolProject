package com.yww.educenter.service;

import com.yww.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yww.educenter.entity.vo.RegisterVo;

/**
 * @author yww
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 登陆
     */
    String login(UcenterMember member);

    /**
     * 注册
     */
    void register(RegisterVo registerVo);
}
