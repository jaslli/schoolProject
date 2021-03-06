package com.yww.educenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName RegisterVo
 * @Descriprtion 注册表单
 * @Author yww
 * @Date 2021/2/18 20:54
 * @Version 1.0
 **/
@Data
public class RegisterVo {

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

}
