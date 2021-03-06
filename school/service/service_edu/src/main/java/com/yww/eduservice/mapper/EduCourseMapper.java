package com.yww.eduservice.mapper;

import com.yww.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yww.eduservice.entity.vo.CoursePublishVo;
import com.yww.eduservice.entity.vo.front.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author yww
 * @since 2021-02-03
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    /**
     * @Descriprtion 获取最终发布课程前的数据
     * @param courseid
     * @return CoursePublishVo
     */
    public CoursePublishVo getPublishCourseInfo(String courseid);

    CourseWebVo getBaseCourseInfo(String courseId);
}
