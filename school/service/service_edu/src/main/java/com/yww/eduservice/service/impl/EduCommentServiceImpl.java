package com.yww.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yww.eduservice.entity.EduComment;
import com.yww.eduservice.mapper.EduCommentMapper;
import com.yww.eduservice.service.EduCommentService;
import org.springframework.stereotype.Service;

/**
 * @ClassName EduCommentServiceImpl
 * @Descriprtion 评论服务的实现类
 * @Author yww
 * @Date 2021/2/21 0:57
 * @Version 1.0
 **/
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {
}
