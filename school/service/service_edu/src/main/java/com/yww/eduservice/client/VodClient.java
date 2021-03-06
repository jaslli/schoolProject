package com.yww.eduservice.client;

import com.yww.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    /**
     * @Descriprtion VOD服务中的通过视频ID删除视频
     * @param videoId 视频ID
     * @return Result
     */
    @DeleteMapping("eduvod/video/deleteVideo/{videoId}")
    Result deleteVideo(@PathVariable("videoId") String videoId);

    /**
     * @Descriprtion 删除多个视频
     * @param videoIdList 视频ID列表
     * @return Result
     */
    @DeleteMapping("eduvod/video/deleteVideos")
    public Result deleteVideos(@RequestParam("videoIdList") List<String> videoIdList);

}

