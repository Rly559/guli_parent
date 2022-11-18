package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    //添加课程基本信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表里添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert <= 0){
            //添加失败
            throw new RuntimeException("添加课程信息失败");
        }

        //获取添加成功之后的课程id
        String id = eduCourse.getId();

        //向课程简介表添加课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id
        courseDescription.setId(id);
        eduCourseDescriptionService.save(courseDescription);
        return id;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //创建courseInfo对象
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        //查询描述表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);

        //将课程表和描述表封装进courseInfo对象
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        courseInfoVo.setDescription(String.valueOf(courseDescription));

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        //修改课程表
        int update = baseMapper.updateById(eduCourse);
        if(update == 0){
            throw new RuntimeException("修改信息失败！");
        }

        //修改描述表
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void deleteAllCourse(String id) {
        //删除课程简介
        eduCourseDescriptionService.removeById(id);
        //查询课程下的章节
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        List<EduChapter> chapterList = eduChapterService.list(queryWrapper);

        //查询课程下的小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        List<EduVideo> videoList = eduVideoService.list(videoQueryWrapper);

        //删除课程下的小节
        for(int i = 0;i < videoList.size();i++){
            String videoId = videoList.get(i).getId();
            EduVideo eduVideo = eduVideoService.getById(videoId);
            String videoSourceId = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                //删除视频
                vodClient.deleteVideoByVideoId(videoSourceId);
            }
            eduVideoService.removeById(videoId);
        }

        //删除课程下的章节
        for(int i = 0;i < chapterList.size();i++){
            String chapterId = chapterList.get(i).getId();
            eduChapterService.removeById(chapterId);
        }

        //删除课程
        baseMapper.deleteById(id);
    }
}
