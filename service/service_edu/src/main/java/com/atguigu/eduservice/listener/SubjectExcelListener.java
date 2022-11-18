package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GlobalExceptionHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/10/5 17:13
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //因为该类不能交给spring进行管理，需要自己new，不能注入其他对象
    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //读取excel内容，一行一行进行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(existOneSubject == null){//没有一级分类，进行添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }

        //获取一级分类的id值
        String pid = existOneSubject.getId();
        //添加二级分类
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null){//没有一级分类，进行添加
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }
    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
