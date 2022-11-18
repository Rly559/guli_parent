package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/11/8 20:14
 */
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    //定义调用方法的路径
    //删除视频方法
    @DeleteMapping("/eduVod/video/{videoId}")
    public R deleteVideoByVideoId(@PathVariable("videoId") String videoId);
}
