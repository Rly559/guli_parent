package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/11/6 17:59
 */
public interface VodService {
    String uploadVideo(MultipartFile file);

    void deleteVideoById(String videoId);
}
