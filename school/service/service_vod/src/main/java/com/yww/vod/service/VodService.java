package com.yww.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName VodService
 * @Descriprtion 视频服务类
 * @Author yww
 * @Date 2021/2/7 22:59
 * @Version 1.0
 **/
public interface VodService {

    /**
     * 上传视频导阿里云
     * @param file 视频文件
     * @return 视频ID
     */
    String uploadVideoAliyun(MultipartFile file);

    /**
     * 批量删除视频
     * @param videoIdList 视频ID列表
     */
    void removeMoreVideo(List<String> videoIdList);

}
