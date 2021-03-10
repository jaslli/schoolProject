package com.yww.eduservice.controller;


import com.yww.commonutils.Result;
import com.yww.eduservice.entity.vo.subject.OneSubject;
import com.yww.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author yww
 * @since 2021-02-02
 */
@Api(tags = "课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    EduSubjectService eduSubjectService;


    @ApiOperation("添加分类")
    @PostMapping("addSuject")
    public Result addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file,eduSubjectService);
        return Result.ok();
    }

    @ApiOperation("分类列表数据")
    @GetMapping("getAllSubject")
    public Result getAllSubject() {
        List<OneSubject> list = eduSubjectService.getSubject();
        return Result.ok().data("list",list);
    }


}

