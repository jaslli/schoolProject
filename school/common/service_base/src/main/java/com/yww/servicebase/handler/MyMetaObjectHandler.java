package com.yww.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName MyMetaObjectHandler
 * @Descriprtion Mybatis-plus的自动插入，注意是属性名而不是数据表字段名
 * @Author yww
 * @Date 2021/1/29 15:07
 * @Version 1.0
 **/

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //  插入时更新创建和修改时间
        this.strictInsertFill(metaObject, "gmtCreate", Date.class, new Date());
        this.strictInsertFill(metaObject, "gmtModified", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //  修改时更新修改时间
        this.strictUpdateFill(metaObject, "gmtModified", Date.class, new Date());
    }


}
