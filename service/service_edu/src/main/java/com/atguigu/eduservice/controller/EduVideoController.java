package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-10-07
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok().data("video",eduVideo);
    }

    //删除小节
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id查询小节对象
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.deleteVideoByVideoId(videoSourceId);
        }
        videoService.removeById(id);
        return R.ok();
    }

    //修改小节
    @PostMapping("{id}")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

    //查询小节
    @GetMapping("{id}")
    public R getVideo(@PathVariable String id){
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("video",eduVideo);
    }
}

