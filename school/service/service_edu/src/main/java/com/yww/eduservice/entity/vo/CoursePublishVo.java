package com.yww.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CoursePublishVo
 * @Descriprtion 课程最终发布前的确认信息封装
 * @Author yww
 * @Date 2021/2/4 22:36
 * @Version 1.0
 **/
@Data
public class CoursePublishVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;

}
