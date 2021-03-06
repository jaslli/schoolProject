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

    String uploadVideoAliyun(MultipartFile file);

    void removeMoreVideo(List<String> videoIdList);

}
