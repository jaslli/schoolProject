package com.yww.aclservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yww.aclservice.entity.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    List<String> selectPermissionValueByUserId(String id);

    List<String> selectAllPermissionValue();

    List<Permission> selectPermissionByUserId(String userId);

}
