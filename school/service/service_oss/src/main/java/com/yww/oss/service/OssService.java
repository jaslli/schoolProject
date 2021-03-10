package com.yww.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author yww
 */
public interface OssService {
    /**
     * 上传头像图片
     * @param file 图片
     * @return 返回引用地址
     */
    String uploadFileAvatar(MultipartFile file);
}
