package com.yww.servicebase.handler.exceptionHandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName schoolException
 * @Descriprtion 自定义异常
 * @Author yww
 * @Date 2021/1/29 17:29
 * @Version 1.0
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class schoolException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    private Integer code;

    private String msg;

}
