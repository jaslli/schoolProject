package com.yww.eduservice.service;

import com.yww.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author yww
 * @since 2021-02-03
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);
}
