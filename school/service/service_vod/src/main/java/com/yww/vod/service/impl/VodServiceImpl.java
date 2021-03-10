package com.yww.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.yww.servicebase.handler.exceptionHandler.schoolException;
import com.yww.vod.service.VodService;
import com.yww.vod.utiles.ConstantVodUtils;
import com.yww.vod.utiles.initVod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @ClassName VodServiceImpl
 * @Descriprtion 实现类
 * @Author yww
 * @Date 2021/2/7 23:00
 * @Version 1.0
 **/
@Service
public class VodServiceImpl implements VodService {
    /**
     * @Descriprtion 视频文件上传
     * @param file 上传的文件
     * @return 返回视频的ID
     */
    @Override
    public String uploadVideoAliyun(MultipartFile file) {
        // 上传文件的名称
        String fileName = file.getOriginalFilename();
        // 上传后的名称
        String title = null;
        if (fileName != null) {
            title = fileName.substring(0, fileName.lastIndexOf("."));
        }
        // 上传文件流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UploadStreamRequest request = new UploadStreamRequest
                (ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

        String videoId = null;
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        //请求视频点播服务的请求ID
        System.out.print("RequestId=" + response.getRequestId() + "\n");

        videoId = response.getVideoId();
        return videoId;
    }

    /**
     * @Descriprtion 批量删除视频
     */
    @Override
    public void removeMoreVideo(List<String> videoIdList) {
        try {
            // 创建初始化对象
            DefaultAcsClient client = initVod.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            // 拼接视频列表的ID
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");

            request.setVideoIds(videoIds);
            // 调用方法删除视频
            client.getAcsResponse(request);

        } catch (Exception e) {
            e.printStackTrace();
            throw new schoolException(20001, "删除视频失败");
        }
    }
}
