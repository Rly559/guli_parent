package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/10/2 18:35
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
