package com.yww.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yww.oss.service.OssService;
import com.yww.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @ClassName OssServiceImpl
 * @Descriprtion OssService的实现类
 * @Author yww
 * @Date 2021/2/1 23:34
 * @Version 1.0
 **/

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        OSS ossClient = null;
        try {
            //  获取OSS实例
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //  文件输入流
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();

            // 为文件名增加UUID，防止上传的文件名称相同冲突
            String uuid = UUID.randomUUID().toString().replaceAll("-","");

            // 把文件通过日期来分类
            String date = new DateTime().toString("yyyy/MM/dd");

            // 构建文件路径
            fileName = date + "/" + uuid + fileName;

            //  上传文件
            ossClient.putObject(bucketName, fileName, inputStream);
            //  获取文件的公网访问路径
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != ossClient) {
                ossClient.shutdown();
            }
        }

    }

}
