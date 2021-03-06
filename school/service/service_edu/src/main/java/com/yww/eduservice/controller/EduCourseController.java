package com.yww.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yww.commonutils.Result;
import com.yww.eduservice.entity.EduCourse;
import com.yww.eduservice.entity.vo.CourseInfoVo;
import com.yww.eduservice.entity.vo.CoursePublishVo;
import com.yww.eduservice.entity.vo.courseQuery;
import com.yww.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author yww
 * @since 2021-02-03
 */

@Api(tags = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation("添加课程基本信息")
    @PostMapping("addCourseInfo")
    public Result addCourseInfo(
            @ApiParam(name = "courseInfoVo", value = "表单对象", required = false)
            @RequestBody CourseInfoVo courseInfoVo) {

        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return Result.ok().data("courseId",id);
    }

    @ApiOperation("根据课程ID查询课程数据")
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId) {

        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfoById(courseId);
        return Result.ok().data("courseInfoVo",courseInfoVo);
    }

    @ApiOperation("条件查询加分页")
    @PostMapping("queryGetInfo/{page}/{limit}")
    public Result queryGetInfo(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "query", value = "查询对象", required = false)
            @RequestBody courseQuery query) {

        Page<EduCourse> coursePage = new Page<>(page,limit);
        eduCourseService.courseQuery(coursePage,query);

        List<EduCourse> records = coursePage.getRecords();
        long total = coursePage.getTotal();
        return Result.ok().data("total", total).data("rows", records);

    }

    @ApiOperation("修改课程信息")
    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(
            @ApiParam(name = "courseInfoVo", value = "表单对象", required = false)
            @RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return Result.ok().data("courseInfoVo",courseInfoVo);
    }

    @ApiOperation("修改课程状态")
    @PostMapping("updateCourseStatus/{id}")
    public Result updateCourseStatus(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        EduCourse eduCourse = new EduCourse();

        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);

        return Result.ok();
    }

    @ApiOperation("根据课程ID查询课程的确认信息")
    @GetMapping("getCourse/{courseId}")
    public Result getCourse(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId) {
        CoursePublishVo info = eduCourseService.getPublishCourseInfo(courseId);
        return Result.ok().data("publishCourse",info);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("`deleteCourse`/{courseId}")
    public Result deleteCourse(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId) {
        eduCourseService.removeCourse(courseId);
        return Result.ok();
    }
}

