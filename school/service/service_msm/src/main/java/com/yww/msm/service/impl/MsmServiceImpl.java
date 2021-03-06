package com.yww.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.yww.msm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @ClassName MsmServiceImpl
 * @Descriprtion MsmService的实现类
 * @Author yww
 * @Date 2021/2/18 17:31
 * @Version 1.0
 **/
@Service
public class MsmServiceImpl implements MsmService {
    /**
     * 发送短信
     * @param param 验证码
     * @param phone 手机号
     * @return true表示发送成功
     */
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        // 设置阿里云的ACCSEEKEY进行连接
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G34bJAoc1TDdU5m5Vn5", "P8MPaR82Un0KIXIguq8oMBpmOFdZTf");
        IAcsClient client = new DefaultAcsClient(profile);

        // 设置相关的参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        // 设置发送的相关参数
        // 设置手机号
        request.putQueryParameter("PhoneNumbers", phone);
        // 设置阿里云短信服务的签名
        request.putQueryParameter("SignName", "yww的博客");
        // 设置阿里云短信服务的模板code
        request.putQueryParameter("TemplateCode", "142325216");
        // 设置验证码（使用JSON）
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        // 最终发送
        try {
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
