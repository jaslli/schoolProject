package com.yww.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yww.eduservice.entity.EduChapter;
import com.yww.eduservice.entity.EduVideo;
import com.yww.eduservice.entity.vo.chapter.ChapterVo;
import com.yww.eduservice.entity.vo.chapter.VideoVo;
import com.yww.eduservice.mapper.EduChapterMapper;
import com.yww.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yww.eduservice.service.EduVideoService;
import com.yww.servicebase.handler.exceptionHandler.schoolException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author yww
 * @since 2021-02-03
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService eduVideoService;

    /**
     * @Descriprtion 根据课程ID查询章节和小节
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //  查询该ID的所有章节
        QueryWrapper<EduChapter> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id", courseId);
        List<EduChapter> chapterList = baseMapper.selectList(wrapper1);

        //  查询所有小节
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id", courseId);
        List<EduVideo> videoList = eduVideoService.list(wrapper2);

        //  最终的结果集合
        List<ChapterVo> resList = new ArrayList<>();

        for (EduChapter chapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            resList.add(chapterVo);

            List<VideoVo> resVideoList = new ArrayList<>();
            for (EduVideo video : videoList) {
                if(video.getChapterId().equals(chapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    resVideoList.add(videoVo);
                }
            }
            chapterVo.setChildren(resVideoList);
        }
        return resList;
    }

    /**
     * @Descriprtion 删除章节
     * @param chapterId
     */
    @Override
    public Boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(wrapper);
        //  该章节存在小节，不允许删除
        if (count > 0) {
            throw new schoolException(20001, "存在小节，不允许删除");
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }
    }

    /**
     * @Descriprtion 根据课程ID删除章节
     * @param courseId
     */
    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }

}
