package com.yww.servicebase.handler.exceptionHandler;

import com.yww.commonutils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @ClassName GlobalExceptionHandler
 * @Descriprtion 统一异常处理类
 * @Author yww
 * @Date 2021/1/29 17:19
 * @Version 1.0
 **/

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.error().message("执行全局异常处理！");
    }

    @ExceptionHandler(schoolException.class)
    @ResponseBody
    public Result error(schoolException e) {
        log.error(e.getMsg());
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }

    @ExceptionHandler(NullDataException.class)
    @ResponseBody
    public Result error(NullDataException e) {
        log.error(e.getMsg());
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }

}
