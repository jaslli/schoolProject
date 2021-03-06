package com.yww.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yww.commonutils.Result;
import com.yww.eduservice.entity.EduCourse;
import com.yww.eduservice.entity.vo.chapter.ChapterVo;
import com.yww.eduservice.entity.vo.front.CourseFrontVo;
import com.yww.eduservice.entity.vo.front.CourseWebVo;
import com.yww.eduservice.service.EduChapterService;
import com.yww.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CourseFrontController
 * @Descriprtion 前台课程信息Controller
 * @Author yww
 * @Date 2021/2/20 8:11
 * @Version 1.0
 **/

@RestController
@CrossOrigin
@RequestMapping("/eduservice/courseFront")
public class CourseFrontController {

    @Autowired
    private EduCourseService service;

    @Autowired
    private EduChapterService chapterService;

    /**
     * 条件分页查询课程数据
     * @param page 当前页
     * @param limit 每页数量
     * @param courseFrontVo 条件封装
     * @return  返回课程数据
     */
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public Result  getFrontCourseList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo) {

        Page<EduCourse> coursePage = new Page<>(page,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        // 一级分类
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        // 二级分类
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        // 销量排序，从大到小
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        // 创建时间排序，从新到旧
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        // 价格排序，从贵到便宜
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("price");
        }

        service.page(coursePage, wrapper);
        long total = coursePage.getTotal();
        List<EduCourse> recods = coursePage.getRecords();
        return Result.ok().data("total",total).data("rows",recods);

    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@PathVariable String courseId) {

        // 根据课程ID，编写SQL语句查询课程信息
        CourseWebVo courseWebVo = service.getBaseCourseInfo(courseId);

        // 根据课程ID查询所有章节和小节
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);

        return Result.ok().data("courseWebVo", courseWebVo).data("chapterVoList", chapterVoList);
    }
}
