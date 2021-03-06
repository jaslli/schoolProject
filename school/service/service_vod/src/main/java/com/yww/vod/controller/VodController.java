package com.yww.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.yww.commonutils.Result;
import com.yww.servicebase.handler.exceptionHandler.schoolException;
import com.yww.vod.service.VodService;
import com.yww.vod.utiles.ConstantVodUtils;
import com.yww.vod.utiles.initVod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName VodController
 * @Descriprtion Controller
 * @Author yww
 * @Date 2021/2/7 22:58
 * @Version 1.0
 **/

@RestController
@Api(tags = {"视频模块"})
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    @ApiOperation("视频上传")
    @PostMapping("uploadVideo")
    public Result uploadVideo(MultipartFile file) {
        String videoId = vodService.uploadVideoAliyun(file);
        return Result.ok().data("videoId",videoId);
    }

    @ApiOperation("批量删除视频")
    @DeleteMapping("deleteVideos")
    public Result deleteVideos(
            @ApiParam(name = "videoIdList",value = "云端视频ID列表",required = true)
            @RequestParam("videoIdList") List<String> videoIdList) {

        vodService.removeMoreVideo(videoIdList);
        return Result.ok();
    }

    @ApiOperation("根据视频ID删除视频")
    @DeleteMapping("deleteVideo/{videoId}")
    public Result deleteVideo(
            @ApiParam(name = "videoId", value = "视频ID", required = true)
            @PathVariable String videoId) {
        try {
            // 创建初始化对象
            DefaultAcsClient client = initVod.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            // 调用方法删除视频
            client.getAcsResponse(request);

            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new schoolException(20001, "删除视频失败");
        }

    }

    @ApiOperation("根据视频ID获取视频凭证")
    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id) {
        try {
            // 创建初始化对象
            DefaultAcsClient client = initVod.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 编辑请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            // 获取凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new schoolException(20001, "获取凭证失败");
        }
    }

}
