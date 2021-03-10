package com.yww.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yww.commonutils.Result;
import com.yww.educms.entity.CmsBanner;
import com.yww.educms.service.CmsBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName BannerAdminController
 * @Descriprtion 后台管理Banner的接口
 * @Author yww
 * @Date 2021/2/10 16:05
 * @Version 1.0
 * TODO 后台管理界面没写
 **/
@RestController
@RequestMapping("/educms/banneradmin")
public class BannerAdminController {

    @Autowired
    private CmsBannerService cmsBannerService;

    @ApiOperation("分页查询")
    @GetMapping("pageBanner/{current}/{limit}")
    public Result pageBanner(
            @PathVariable long current,
            @PathVariable long limit) {

        Page<CmsBanner> page = new Page<>(current, limit);
        cmsBannerService.page(page, null);
        return Result.ok().data("items", page.getRecords()).data("total", page.getTotal());
    }

    @ApiOperation("根据ID查询")
    @GetMapping("getById/{id}")
    public Result getById(@PathVariable long id) {
        CmsBanner cmsBanner = cmsBannerService.getById(id);
        return Result.ok().data("item", cmsBanner);
    }

    @ApiOperation("增加")
    @PostMapping("addBanner")
    public Result addBanner(@RequestBody CmsBanner cmsBanner) {
        cmsBannerService.save(cmsBanner);
        return Result.ok();
    }

    @ApiOperation("修改")
    @PostMapping("update")
    public Result updateBanner(@RequestBody CmsBanner cmsBanner) {
        cmsBannerService.updateById(cmsBanner);
        return Result.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping("delete/{id}")
    public Result deleteBanner(@PathVariable long id) {
        cmsBannerService.removeById(id);
        return Result.ok();
    }


}

