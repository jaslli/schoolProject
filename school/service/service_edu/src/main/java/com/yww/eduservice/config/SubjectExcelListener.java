package com.yww.eduservice.config;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yww.eduservice.entity.EduSubject;
import com.yww.eduservice.entity.excel.SubjectData;
import com.yww.eduservice.service.EduSubjectService;
import com.yww.servicebase.handler.exceptionHandler.NullDataException;

/**
 * @ClassName SubjectExcelListener
 * @Descriprtion EasyExcel读取的监听器
 * @Author yww
 * @Date 2021/2/2 16:35
 * @Version 1.0
 **/
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    /**
     * @Descriprtion 读取Excel中的内容
     * @param subjectData
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null) {
            throw new NullDataException(20001, "文件数据为空");
        }
        // 添加一级分类
        EduSubject OneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if(OneSubject == null) {
            OneSubject = new EduSubject();
            OneSubject.setParentId("0");
            OneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(OneSubject);
        }

        // 添加二级分类
        String pid = OneSubject.getId();
        EduSubject TwoSubject = this.existTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(), pid);
        if(TwoSubject == null) {
            TwoSubject = new EduSubject();
            TwoSubject.setParentId(pid);
            TwoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(TwoSubject);
        }


    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    /**
     * @Descriprtion 查询该一级分类是否存在
     * @param eduSubjectService
     * @param name
     * @return EduSubject
     */
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id", "0");
        EduSubject subject = eduSubjectService.getOne(wrapper);
        return subject;
    }

    /**
     * @Descriprtion 查询该二级分类是否存在在该一级分类
     * @param eduSubjectService
     * @param name
     * @param pid
     * @return EduSubject
     */
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id", pid);
        EduSubject subject = eduSubjectService.getOne(wrapper);
        return subject;
    }


    // 因为该实现类无法使用注入eduSubjectService，所以使用有参构造手动注入
    public EduSubjectService eduSubjectService;
    public SubjectExcelListener(){}
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

}
