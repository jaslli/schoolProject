package com.yww.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yww.commonutils.Result;
import com.yww.eduservice.entity.EduCourse;
import com.yww.eduservice.entity.EduTeacher;
import com.yww.eduservice.service.EduCourseService;
import com.yww.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName TeacherFrontController
 * @Descriprtion 前台讲师Controller
 * @Author yww
 * @Date 2021/2/20 5:26
 * @Version 1.0
 **/
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherFront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherservice;

    @Autowired
    private EduCourseService courseService;

    /**
     * 分页查询
     * @param page 当前页
     * @param limit 每页数据量
     * @return 分页后的数据
     */
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable long page,@PathVariable long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper();
        wrapper.orderByDesc("id");
        teacherservice.page(teacherPage);
        long total = teacherPage.getTotal();
        List<EduTeacher> recodes = teacherPage.getRecords();
        return Result.ok().data("total",total).data("rows",recodes);
    }

    /**
     * 根据讲师ID查询基本信息和课程信息
     * @param id 讲师ID
     * @return 讲师基本信息和课程
     */
    @GetMapping("getTeacherFrontInfo/{id}")
    public Result getTeacherFrontInfo(@PathVariable String id) {

        // 根据讲师ID查询基本信息
        EduTeacher teacher = teacherservice.getById(id);

        // 根据讲师ID查询课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<EduCourse> courseList = courseService.list(wrapper);

        return Result.ok().data("teacher",teacher).data("courseList",courseList);
    }


}
