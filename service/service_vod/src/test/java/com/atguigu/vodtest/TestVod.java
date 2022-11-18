package com.atguigu.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import java.util.List;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/11/6 16:34
 */
public class TestVod {
    public static void main(String[] args) {
        String accessKeyId = "LTAI5tBbFq3CB5nMn9eT5qmv";
        String assessKeySecret = "R2vvVB2G7Fe9WtXE0FRBmeI3l8JptI";

        String title = "6 - What If I Want to Move Faster.mp4";//上传之后文件名称
        String filename = "D:\\学习资料\\谷粒学苑\\项目资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4";//本地文件路径和名称
        //上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId,assessKeySecret,title,filename);
        //指定分片上传时每个分片的大小
        request.setPartSize(2 * 1024 * 1024L);
        //可指定分片并发线程数
        request.setTaskNum(1);

        UploadVideoImpl upload = new UploadVideoImpl();
        UploadVideoResponse response = upload.uploadVideo(request);

        System.out.print("RequestId = " + response.getRequestId() + "\n"); // 请求视频点播服务的请求Id
        if(response.isSuccess()){
            System.out.print("VideoId = " + response.getVideoId() + "\n");
        }else{
            System.out.print("VideoId = " + response.getVideoId() + "\n");
            System.out.print("ErrorCode = " + response.getCode() + "\n");
            System.out.print("ErrorMessage = " + response.getMessage() + "\n");
        }
    }

    public static void getVedioUrl(){
        //根据视频id获取视频播放地址
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tBbFq3CB5nMn9eT5qmv", "R2vvVB2G7Fe9WtXE0FRBmeI3l8JptI");
        //创建获取视频地址request和resonse
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //向request对象设置视频id
        request.setVideoId("3c0ab510734a457da2fd855f28aaf9c0");
        //调用初始化对象里面的方法，传递request 获取数据
        try {
            response = client.getAcsResponse(request);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}


