package com.yww.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yww.eduservice.client.VodClient;
import com.yww.eduservice.entity.EduVideo;
import com.yww.eduservice.mapper.EduVideoMapper;
import com.yww.eduservice.service.EduVideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author yww
 * @since 2021-02-03
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    /**
     * @Descriprtion 根据课程ID删除其中的小节和视频
     * @param courseId
     */
    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideos =  baseMapper.selectList(wrapper);

        List<String> videoIds = new ArrayList<>();
        for (EduVideo video : eduVideos) {
            if(!StringUtils.isEmpty(video.getVideoSourceId())) {
                videoIds.add(video.getVideoSourceId());
            }
        }
        if (videoIds.size() > 0) {
            vodClient.deleteVideos(videoIds);
        }

    }

}
