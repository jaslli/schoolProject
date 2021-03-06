package com.yww.msm.controller;

import com.yww.commonutils.Result;
import com.yww.msm.service.MsmService;
import com.yww.msm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MsmController
 * @Descriprtion Msm模块的Controller
 * @Author yww
 * @Date 2021/2/18 17:29
 * @Version 1.0
 **/

@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("send/{phone}")
    public Result sendMsm(@PathVariable String phone) {
        // 判断缓存中是否有验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return Result.ok();
        }

        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = msmService.send(param,phone);
        if(isSend) {
            // 将验证码加入缓存，并设置有效时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error();
        }
    }

}
