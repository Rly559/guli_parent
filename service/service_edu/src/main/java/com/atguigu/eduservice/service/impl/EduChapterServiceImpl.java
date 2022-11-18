package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @author testjava
 * @since 2022-10-07
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    //课程大纲列表,根据课程id查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据课程id查询课程章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> chapterList = baseMapper.selectList(wrapperChapter);
        //根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //创建list集合 用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();
        //遍历查询章节list集合进行封装
        for(int i = 0;i < chapterList.size();i++){
            //得到每一个chapter对象
            EduChapter eduChapter = chapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            //把chapterVo放入finalList
            finalList.add(chapterVo);

            //创建集合 用于封装小节
            List<VideoVo> videoList = new ArrayList<>();
            //遍历查询小节list集合进行封装
            for(int m = 0;m < eduVideoList.size();m++) {
                EduVideo eduVideo = eduVideoList.get(m);
                //判断 小节里面chapterId和章节里得id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }

    @Override
    public void deleteChapter(String chapterId) {
        //根据章节id查询小节
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        int count = videoService.count(queryWrapper);
        if(count == 0){
            baseMapper.deleteById(chapterId);
        }else{
            throw new RuntimeException("章节下还有小节，无法删除！");
        }
    }
}
