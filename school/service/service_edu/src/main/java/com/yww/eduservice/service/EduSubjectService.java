package com.yww.eduservice.service;

import com.yww.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yww.eduservice.entity.vo.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author yww
 * @since 2021-02-02
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> getSubject();
}
