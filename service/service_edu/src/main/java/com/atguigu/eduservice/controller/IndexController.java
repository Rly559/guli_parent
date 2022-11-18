package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/11/10 17:04
 */
@RestController
@RequestMapping("/eduservice/index")
@CrossOrigin
public class IndexController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @Cacheable
    @GetMapping("index")
    public R index(){
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(wrapper);

        //查询前4条名师
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(queryWrapper);
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }

}
