package com.yww.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yww.commonutils.Result;
import com.yww.eduservice.entity.EduTeacher;
import com.yww.eduservice.entity.vo.TeacherQuery;
import com.yww.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author yww
 * @since 2021-01-28
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有讲师数据的方法
     * @return
     */
    @ApiOperation("查询所有讲师")
    @GetMapping("findAll")
    public Result findAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return Result.ok().data("items",list);
    }

    /**
     * @Descriprtion 根据ID来查询讲师
     * @param id
     * @return
     */
    @ApiOperation("根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public Result getTeacher(
            @ApiParam(name = "id", value = "讲师的ID", required = true)
            @PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        return Result.ok().data("items", teacher);
    }

    /**
     * 分页查询讲师的方法
     * 1. 配置分页插插件
     * @param current
     * @param limit
     * @return
     */
    @ApiOperation("分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public Result pageListTeacher(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit) {
        Page<EduTeacher> page = new Page<>(current,limit);
        eduTeacherService.page(page, null);
        long total = page.getTotal();
        List<EduTeacher> recodes = page.getRecords();
        return Result.ok().data("total",total).data("rows",recodes);
    }

    /**
     * 条件查询
     * 1. 使用到分页查询
     * 2. 使用封装类返回数据
     * 3. 在service中增加方法，并实现
     * @param current
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation("条件查询讲师")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public Result pageTeacherCondition(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit,
            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
            @RequestBody TeacherQuery teacherQuery) {
        Page<EduTeacher> page = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        wrapper.orderByDesc("gmt_create");

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level) ) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }


        eduTeacherService.page(page,wrapper);
        long total = page.getTotal();
        List<EduTeacher> recodes = page.getRecords();
        return Result.ok().data("total",total).data("rows",recodes);
    }


    /**
     * @Descriprtion 添加讲师的方法
     * 1. 涉及到自动更新创建时间和修改时间，先为属性增加@TableField注解
     * 2. 在全局配置自动注入的设置
     * @param eduTeacher
     * @return
     */
    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    public Result save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }


    /**
     * @Descriprtion 通过ID来修改讲师
     * @param eduTeacher
     * @return
     */
    @ApiOperation("根据ID修改讲师")
    @PostMapping("updateTeacher")
    public Result updateTeacher(
            @ApiParam(name = "teacher",value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }


    /**
     * 逻辑删除讲师的方法
     * 1. 添加逻辑删除的插件
     * 2. 为逻辑删除的属性增加@TableLogic注解
     * @param id
     * @return
     */
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("{id}")
    public Result removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}

