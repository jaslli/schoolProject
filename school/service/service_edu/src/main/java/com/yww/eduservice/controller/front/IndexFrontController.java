package com.yww.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yww.commonutils.Result;
import com.yww.eduservice.entity.EduCourse;
import com.yww.eduservice.entity.EduTeacher;
import com.yww.eduservice.service.EduCourseService;
import com.yww.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName IndexFrontController
 * @Descriprtion 前台页面接口
 * @Author yww
 * @Date 2021/2/10 16:40
 * @Version 1.0
 **/

@CrossOrigin
@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    @Autowired
    EduCourseService courseService;

    @Autowired
    EduTeacherService teacherService;

    @ApiOperation("查询八个热门课程和前四名名师")
    @GetMapping("index")
    public Result index() {
        // 课程
        QueryWrapper<EduCourse> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("view_count");
        wrapper1.last("limit 8");
        List<EduCourse> courseList = courseService.list(wrapper1);

        // 讲师
        QueryWrapper<EduTeacher> wrapper2 = new QueryWrapper<>();
        wrapper2.orderByAsc("id");
        wrapper2.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapper2);

        return Result.ok().data("courseList", courseList).data("teacherList", teacherList);
    }

}
