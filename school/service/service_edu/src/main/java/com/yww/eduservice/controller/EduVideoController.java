package com.yww.eduservice.controller;


import com.alibaba.excel.util.StringUtils;
import com.yww.commonutils.Result;
import com.yww.eduservice.client.VodClient;
import com.yww.eduservice.entity.EduVideo;
import com.yww.eduservice.service.EduVideoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author yww
 * @since 2021-02-03
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient;

    @ApiOperation("根据小节ID查询小节内容")
    @GetMapping("getVideoById/{videoId}")
    public Result getVideoById(
            @ApiParam(name = "videoId", value = "小节ID", required = true)
            @PathVariable String videoId) {

        EduVideo eduVideo = eduVideoService.getById(videoId);

        return Result.ok().data("video",eduVideo);
    }


    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public Result addVideo(
            @ApiParam(name = "video", value = "小节内容", required = true)
            @RequestBody EduVideo video) {
        eduVideoService.save(video);
        return Result.ok();
    }

    /**
     * @param videoId
     * @return
     */
    @ApiOperation("删除小节")
    @DeleteMapping("deleteVideo/{videoId}")
    public Result deleteVideo(
            @ApiParam(name = "videoId", value = "小节ID", required = true)
            @PathVariable String videoId) {

        // 通过小节ID获取视频ID,从而删除视频
        EduVideo video = eduVideoService.getById(videoId);
        String id = video.getVideoSourceId();
        if(!StringUtils.isEmpty(id)) {
            vodClient.deleteVideo(id);
        }

        // 删除小节
        eduVideoService.removeById(videoId);

        return Result.ok();
    }

    @ApiOperation("修改小节")
    @PostMapping("updateVideo")
    public Result updateVideo(
            @ApiParam(name = "chapter", value = "章节内容", required = true)
            @RequestBody EduVideo video) {
        eduVideoService.updateById(video);
        return Result.ok();
    }


}

