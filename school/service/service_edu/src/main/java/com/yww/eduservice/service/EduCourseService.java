package com.yww.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yww.eduservice.entity.EduCourse;
import com.yww.eduservice.entity.vo.CourseInfoVo;
import com.yww.eduservice.entity.vo.CoursePublishVo;
import com.yww.eduservice.entity.vo.courseQuery;
import com.yww.eduservice.entity.vo.front.CourseWebVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yww
 * @since 2021-02-03
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoById(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String courseId);

    void courseQuery(Page<EduCourse> page, courseQuery courseQuery);

    void removeCourse(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
