package com.atguigu.vod.controller;

import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/11/6 17:58
 */
@RestController
@RequestMapping("/eduVod/video")
@CrossOrigin//解决跨域
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频方法
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }

    //删除视频方法
    @DeleteMapping("{videoId}")
    public R deleteVideoByVideoId(@PathVariable String videoId){
        vodService.deleteVideoById(videoId);
        return R.ok();
    }
}
