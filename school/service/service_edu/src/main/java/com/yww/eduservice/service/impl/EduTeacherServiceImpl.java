package com.yww.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yww.eduservice.entity.EduTeacher;
import com.yww.eduservice.mapper.EduTeacherMapper;
import com.yww.eduservice.service.EduTeacherService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author yww
 * @since 2021-01-28
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
}
