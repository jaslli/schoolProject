package com.yww.eduservice.entity.vo.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OneSubject
 * @Descriprtion 一级分类的JSON数据封装
 * @Author yww
 * @Date 2021/2/2 18:58
 * @Version 1.0
 **/
@Data
public class OneSubject {

    private String id;

    private String title;

    private List<TwoSubject> children = new ArrayList<>();

}
