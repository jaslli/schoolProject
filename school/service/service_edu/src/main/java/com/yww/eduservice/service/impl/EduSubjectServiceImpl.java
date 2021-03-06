package com.yww.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yww.eduservice.config.SubjectExcelListener;
import com.yww.eduservice.entity.EduSubject;
import com.yww.eduservice.entity.excel.SubjectData;
import com.yww.eduservice.entity.vo.subject.OneSubject;
import com.yww.eduservice.entity.vo.subject.TwoSubject;
import com.yww.eduservice.mapper.EduSubjectMapper;
import com.yww.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author yww
 * @since 2021-02-02
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * @Descriprtion 添加分类的方法
     * @param file
     * @param eduSubjectService
     */
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getSubject() {
        // 创建结果集合,也就是封装好的JSON数据
        List<OneSubject> res = new ArrayList<>();

        // 查询所有的一级分类
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapper1);

        // 查询所有的二级分类
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapper2);


        // 封装一级分类
        for (EduSubject eduSubject1 : oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
            // 将eduSubject中的id和title复制到oneSubject
            BeanUtils.copyProperties(eduSubject1, oneSubject);
            res.add(oneSubject);

            // 封装二级分类
            List<TwoSubject> twoSubjects = new ArrayList<>();
            for(EduSubject eduSubject2 : twoSubjectList) {
                if(eduSubject2.getParentId().equals(eduSubject1.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    twoSubject.setID(eduSubject2.getId());
                    twoSubject.setTitle(eduSubject2.getTitle());
                    twoSubjects.add(twoSubject);
                }
            }

            oneSubject.setChildren(twoSubjects);
        }

        return res;
    }
}
