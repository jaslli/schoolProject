package com.yww.eduservice.service;

import com.yww.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yww.eduservice.entity.vo.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yww
 * @since 2021-02-03
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    Boolean deleteChapter(String chapterId);

    void removeByCourseId(String courseId);
}
