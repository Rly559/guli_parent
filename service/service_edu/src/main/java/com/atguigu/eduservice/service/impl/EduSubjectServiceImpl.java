package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-10-05
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {

        InputStream in = null;
        try {
            //文件输入流
            in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //课程分类列表(树形)
    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //1 查询所有一级分类    parent_id = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", 0);
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //2 查询所有的二级分类  parent_id != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", 0);
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合 用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //3 封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值
        //封装到要求的list集合里面List<OneSubject> finalSubjectList
        for(int i = 0;i < oneSubjectList.size();i ++){//遍历oneSubject集合
            //得到每一个oneSubjectList每个eduSubject对象
            EduSubject eduSubject = oneSubjectList.get(i);
            //把eduSubject里面值获取出来 放到Onesubject对象里面
            //多个OneSubject放到finalSubjectList里面
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());    ====   BeanUtils.copyProperties(eduSubject, oneSubject);

            //把eduSubject的值复制到oneSubject
            BeanUtils.copyProperties(eduSubject, oneSubject);

            finalSubjectList.add(oneSubject);

            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();

            //4 封装二级分类
            //遍历二级分类list集合
            for(int m = 0;m < twoSubjectList.size();m ++){//遍历oneSubject集合
                //得到每一个二级分类
                EduSubject tSubject = twoSubjectList.get(m);
                //判断二级分类属于哪一个一级分类
                if(tSubject.getParentId().equals(eduSubject.getId())){
                    //把tSubject的值复制到TwoSubject里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }

            }

            //把一级分类下所有的二级分类封装到一级分类里
            oneSubject.setChildren(twoFinalSubjectList);

        }

        return finalSubjectList;
    }
}
