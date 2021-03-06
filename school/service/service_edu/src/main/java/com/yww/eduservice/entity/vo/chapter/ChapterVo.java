package com.yww.eduservice.entity.vo.chapter;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ChapterVo
 * @Descriprtion 章节封装类
 * @Author yww
 * @Date 2021/2/3 23:05
 * @Version 1.0
 **/
@Data
@ApiModel(value = "课程的章节部分", description = "编辑课程章节的信息")
public class ChapterVo {

    private String id;

    private String title;

    private List<VideoVo> children = new ArrayList<>();

}
