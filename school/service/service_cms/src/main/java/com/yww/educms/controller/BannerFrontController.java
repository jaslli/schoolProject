package com.yww.educms.controller;

import com.yww.commonutils.Result;
import com.yww.educms.entity.CmsBanner;
import com.yww.educms.service.CmsBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName BannerFrontController
 * @Descriprtion 前台Banner的接口
 * @Author yww
 * @Date 2021/2/10 16:05
 * @Version 1.0
 **/

@RestController
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {

    @Autowired
    private CmsBannerService cmsBannerService;

    @ApiOperation("查询三条记录")
    @GetMapping("find")
    public Result getAllBanner() {
        List<CmsBanner> list = cmsBannerService.selectBanner();
        return Result.ok().data("list", list);
    }

}
