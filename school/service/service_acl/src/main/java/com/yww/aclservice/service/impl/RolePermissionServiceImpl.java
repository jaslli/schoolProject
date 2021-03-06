package com.yww.aclservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yww.aclservice.entity.RolePermission;
import com.yww.aclservice.mapper.RolePermissionMapper;
import com.yww.aclservice.service.RolePermissionService;
import org.springframework.stereotype.Service;

/**
 * @ClassName RolePermissionServiceImpl
 * @Descriprtion TODO
 * @Author yww
 * @Date 2021/2/25 11:40
 * @Version 1.0
 **/
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
}
