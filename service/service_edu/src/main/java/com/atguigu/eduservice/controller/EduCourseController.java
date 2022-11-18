package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-10-07
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;


    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加之后课程id，为了后续添加课程大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //根据课程id修改课程基本信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //课程最终发布
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置发布状态
        courseService.updateById(eduCourse);
        return R.ok();
    }

    //课程列表
    //条件查询 + 分页
        @ApiOperation(value = "条件组合查询")
        @PostMapping ("pageCourseCondition/{current}/{limit}")
        public R pageCourseCondition(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) CourseQuery courseQuery){
            //创建page对象
            Page<EduCourse> pageCourse = new Page<>(current,limit);
            //构建条件
            QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

            //多条件组合查询(判断条件是否为空)
            String name = courseQuery.getTitle();
            String status = courseQuery.getStatus();

            //拼接wrapper
            if(!StringUtils.isEmpty(name)){
                wrapper.like("title", name);
            }
            if(!StringUtils.isEmpty(status)){
                wrapper.eq("status", status);
            }

            //排序
            wrapper.orderByDesc("gmt_create");

            //调用方法实现条件查询
            courseService.page(pageCourse,wrapper);
            long total = pageCourse.getTotal();//总记录数
            List<EduCourse> records = pageCourse.getRecords();//数据list集合

    //        Map map = new HashMap();
    //        map.put("total", total);
    //        map.put("rows", records);
    //        return R.ok().data(map);

            return R.ok().data("total", total).data("rows", records);
        }

    //课程最终发布
    @PostMapping("updateStatusCourse/{id}")
    public R updateStatusCourse(@PathVariable String id){
        EduCourse eduCourse = courseService.getById(id);
        String status = eduCourse.getStatus();
        if(status.equals("Normal")){
            eduCourse.setStatus("Draft");
            courseService.updateById(eduCourse);
        }else{
            eduCourse.setStatus("Normal");
            courseService.updateById(eduCourse);
        }
        return R.ok();
    }

    //删除课程
    @DeleteMapping("deleteCourse/{id}")
    public R deleteCourse(@PathVariable String id){
        courseService.deleteAllCourse(id);
        return R.ok();
    }
}

