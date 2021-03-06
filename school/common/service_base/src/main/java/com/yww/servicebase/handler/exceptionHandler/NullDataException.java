package com.yww.servicebase.handler.exceptionHandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName NullDataException
 * @Descriprtion NullDataException
 * @Author yww
 * @Date 2021/2/2 18:41
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NullDataException extends RuntimeException {
    @ApiModelProperty(value = "状态码")
    private Integer code;

    private String msg;
}
