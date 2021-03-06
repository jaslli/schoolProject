package com.yww.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yww.eduservice.entity.EduCourse;
import com.yww.eduservice.entity.EduCourseDescription;
import com.yww.eduservice.entity.vo.CourseInfoVo;
import com.yww.eduservice.entity.vo.CoursePublishVo;
import com.yww.eduservice.entity.vo.courseQuery;
import com.yww.eduservice.entity.vo.front.CourseWebVo;
import com.yww.eduservice.mapper.EduCourseMapper;
import com.yww.eduservice.service.EduChapterService;
import com.yww.eduservice.service.EduCourseDescriptionService;
import com.yww.eduservice.service.EduCourseService;
import com.yww.eduservice.service.EduVideoService;
import com.yww.servicebase.handler.exceptionHandler.schoolException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author yww
 * @since 2021-02-03
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * @Descriprtion 添加课程信息
     * @param courseInfoVo
     * @return
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 像课程表中添加课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert <= 0) {
            throw new schoolException(20001, "添加课程信息失败");
        }
        // 获取生成的课程ID
        String cid = eduCourse.getId();

        // 向课程描述表中添加描述信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        // 表示一对一对应，需要修改主键生成策略
        courseDescription.setId(cid);
        eduCourseDescriptionService.save(courseDescription);

        return cid;
    }

    /**
     * @Descriprtion 根据课程ID查询课程信息
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {

        EduCourse eduCourse = baseMapper.selectById(courseId);

        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);

        CourseInfoVo courseInfoVo = new CourseInfoVo();

        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

    /**
     * @Descriprtion 修改课程信息
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0) {
            throw new schoolException(20001, "修改课程信息失败");
        }

        // 修改课程描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(description);
    }

    /**
     * @Descriprtion 根据课程ID查询最终确认信息
     * @param courseId
     * @return CoursePublishVo
     */
    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {

        CoursePublishVo info = baseMapper.getPublishCourseInfo(courseId);

        return info;
    }

    /**
     * @Descriprtion 条件查询带分页，查询课程信息
     * @param page
     * @param query
     */
    @Override
    public void courseQuery(Page<EduCourse> page, courseQuery query) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper();
        wrapper.orderByDesc("gmt_create");

        if(query == null) {
            baseMapper.selectPage(page, wrapper);
            return;
        }

        String title = query.getTitle();
        String teacherId = query.getTeacherId();
        String subjectParentId = query.getSubjectParentId();
        String subjectId = query.getSubjectId();
        String status = query.getStatus();

        if(!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if(!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id", teacherId);
        }
        if(!StringUtils.isEmpty(subjectParentId)) {
            wrapper.ge("subject_parent_id", subjectParentId);
        }
        if(!StringUtils.isEmpty(subjectId)) {
            wrapper.ge("subject_id", subjectId);
        }
        if(!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }

        baseMapper.selectPage(page, wrapper);

    }

    /**
     * @Descriprtion 删除课程，先删除小节，删除章节，删除描述，最后删除课程本身
     * @param courseId
     */
    @Override
    public void removeCourse(String courseId) {
        // 删除小节
        eduVideoService.removeByCourseId(courseId);

        // 删除章节
        eduChapterService.removeByCourseId(courseId);

        // 删除描述
        eduCourseDescriptionService.removeById(courseId);

        // 删除课程
        int count = baseMapper.deleteById(courseId);

        if(count == 0) {
            throw new schoolException(20001, "删除失败");
        }

    }

    /**
     * 根据课程ID查询课程详情页所需要的信息
     * @param courseId 课程ID
     * @return 课程详情页的基本信息
     */
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
