package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantVodUtils;
import com.atguigu.vod.utils.InitVodClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/11/6 17:59
 */
@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            //filename 上传文件原始名称
            String fileName = file.getOriginalFilename();
            //title 名称
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            //inputStream 上传文件流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.KEY_ID,ConstantVodUtils.KEY_SECRET,title,fileName,inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");//请求视频点播服务的请求id
            String videoId = null;
            if(response.isSuccess()){
                System.out.print("videoId = " + response.getVideoId() + "\n");
                videoId = response.getVideoId();
            }else{
                System.out.print("videoId = " + response.getVideoId() + "\n");
                System.out.print("ErrorCode = " + response.getCode() + "\n");
                System.out.print("ErrorMessage = " + response.getMessage() + "\n");
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteVideoById(String videoId) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.KEY_ID, ConstantVodUtils.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print(response.getRequestId());
        }catch (Exception e){
            throw new RuntimeException("删除视频失败");
        }
    }
}
