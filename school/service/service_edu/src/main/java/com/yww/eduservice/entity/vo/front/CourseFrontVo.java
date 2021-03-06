package com.yww.eduservice.entity.vo.front;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName CourseFrontVo
 * @Descriprtion 课程详情封装
 * @Author yww
 * @Date 2021/2/20 8:07
 * @Version 1.0
 **/
@Data
public class CourseFrontVo {

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "一级分类ID")
    private String subjectParentId;

    @ApiModelProperty(value = "二级分类ID")
    private String subjectId;

    @ApiModelProperty(value = "销量排序")
    private String buyCountSort;

    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;

    @ApiModelProperty(value = "价格排序")
    private String priceSort;

}
