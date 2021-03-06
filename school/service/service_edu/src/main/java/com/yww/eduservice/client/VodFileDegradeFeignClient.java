package com.yww.eduservice.client;

import com.yww.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName VodFileDegradeFeignClient
 * @Descriprtion 服务熔断实现类
 * @Author yww
 * @Date 2021/2/10 14:15
 * @Version 1.0
 **/

@Component
public class VodFileDegradeFeignClient implements VodClient{

    @Override
    public Result deleteVideo(String videoId) {
        return Result.error().message("删除视频出错了");
    }

    @Override
    public Result deleteVideos(List<String> videoIdList) {
        return Result.error().message("删除多个视频出错了");
    }

}
