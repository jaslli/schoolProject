package com.yww.eduservice.controller;


import com.yww.commonutils.Result;
import com.yww.eduservice.entity.EduChapter;
import com.yww.eduservice.entity.vo.chapter.ChapterVo;
import com.yww.eduservice.service.EduChapterService;
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
@RestController

@Api(tags = "章节管理")
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation("根据ID查询课程的章节和小节")
    @GetMapping("getChapterInfo/{courseId}")
    public Result getChapterAndVideo(
            @ApiParam(name = "courseId", value = "课程的ID", required = true)
            @PathVariable String courseId) {

        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);

        return Result.ok().data("allChapterVideo",list);
    }


    @ApiOperation("章节的添加")
    @PostMapping("addChapter")
    public Result addChapter(
            @ApiParam(name = "chapter", value = "章节内容", required = true)
            @RequestBody EduChapter chapter) {
        eduChapterService.save(chapter);
        return Result.ok();
    }

    @ApiOperation("根据ID查询章节")
    @GetMapping("getChapterById/{chapterId}")
    public Result getChapterById(
            @ApiParam(name = "chapterId", value = "章节的ID", required = true)
            @PathVariable String chapterId) {
        EduChapter chapter = eduChapterService.getById(chapterId);
        return Result.ok().data("chapter",chapter);
    }

    @ApiOperation("章节的修改")
    @PostMapping("updateChapter")
    public Result updateChapter(
            @ApiParam(name = "chapter", value = "章节内容", required = true)
            @RequestBody EduChapter chapter) {
        eduChapterService.updateById(chapter);
        return Result.ok();
    }

    @ApiOperation("章节的删除")
    @DeleteMapping("deleteChapter/{chapterId}")
    public Result deleteChapter(
            @ApiParam(name = "chapterId", value = "章节的ID", required = true)
            @PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if(flag) {
            return Result.ok();
        } else {
            return Result.error();
        }

    }


}

