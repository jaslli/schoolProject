package com.yww.msm.service;

import java.util.Map;

/**
 * @ClassName MsmService
 * @Descriprtion Msm模块的Service
 * @Author yww
 * @Date 2021/2/18 17:30
 * @Version 1.0
 **/
public interface MsmService {
    boolean send(Map<String, Object> param, String phone);
}
