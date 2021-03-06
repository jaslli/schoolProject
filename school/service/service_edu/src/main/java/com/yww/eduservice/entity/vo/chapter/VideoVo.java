package com.yww.eduservice.entity.vo.chapter;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @ClassName VideoVo
 * @Descriprtion 小节封装类
 * @Author yww
 * @Date 2021/2/3 23:06
 * @Version 1.0
 **/
@Data
@ApiModel(value = "课程的小节部分", description = "编辑课程小节的信息")
public class VideoVo {

    private String id;

    private String title;

    private String videoSourceId;

}
