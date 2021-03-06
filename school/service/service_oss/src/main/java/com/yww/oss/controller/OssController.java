package com.yww.oss.controller;

import com.yww.commonutils.Result;
import com.yww.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName OssController
 * @Descriprtion OSS的controller类
 * @Author yww
 * @Date 2021/2/1 23:34
 * @Version 1.0
 **/
@Api(tags = "OSS的Controller")
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    @ApiOperation("上传文件")
    @PostMapping
    public Result uploadOssFile(
            @ApiParam(name = "file", value = "文件", required = true)
            MultipartFile file) {

        String url = ossService.uploadFileAvatar(file);

        return Result.ok().data("url",url);
    }
}
