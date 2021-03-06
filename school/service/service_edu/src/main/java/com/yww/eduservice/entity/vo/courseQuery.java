package com.yww.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CourseQuery
 * @Descriprtion 课程列表的封装数据
 * @Author yww
 * @Date 2021/2/4 23:48
 * @Version 1.0
 **/
@Data
@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
public class courseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称")
    private String title;
    @ApiModelProperty(value = "讲师id")
    private String teacherId;
    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;
    @ApiModelProperty(value = "二级类别id")
    private String subjectId;
    @ApiModelProperty(value = "课程状态")
    private String Status;

}
