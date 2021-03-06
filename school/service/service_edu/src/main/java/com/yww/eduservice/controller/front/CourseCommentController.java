package com.yww.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yww.commonutils.JwtUtils;
import com.yww.commonutils.Result;
import com.yww.eduservice.entity.EduComment;
import com.yww.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName CourseCommentController
 * @Descriprtion 课程评论的Controller
 * @Author yww
 * @Date 2021/2/21 0:46
 * @Version 1.0
 **/
@CrossOrigin
@RestController
@RequestMapping("/eduservice/comment")
public class CourseCommentController {

    @Autowired
    EduCommentService service;

    /**
     * 根据课程ID分页查询评论数据
     * @param courseId 课程ID
     * @param current   当前页
     * @param limit     每页数量
     * @return  每页的数据
     */
    @GetMapping("getComment/{courseId}/{current}/{limit}")
    public Result getComment(@PathVariable String courseId,@PathVariable long current,@PathVariable long limit) {
        Page<EduComment> page = new Page<>(current,limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.eq("course_id", courseId);
        service.page(page, wrapper);
        List<EduComment> records = page.getRecords();
        long total = page.getTotal();
        return Result.ok().data("total",total).data("rows",records);
    }

    /**
     * 添加评论
     * @param comment 评论信息
     * @return  true表示添加成功
     */
    @PostMapping("addComment")
    public Result addComment(@RequestBody EduComment comment, HttpServletRequest request) {
        if (StringUtils.isEmpty(JwtUtils.getMemberIdByJwtToken(request))) {
            return Result.error().message("请登录后在操作！");
        }
        service.save(comment);
        return Result.ok();
    }


}
