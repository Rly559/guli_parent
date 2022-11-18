package com.atguigu.oss.service.impl;

import com.atguigu.oss.service.OssService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static com.atguigu.oss.utils.ConstantPropertiesUtils.*;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/10/2 18:36
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
                // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
                String endpoint = END_POINT;
                // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
                String accessKeyId = KEY_ID;
                String accessKeySecret = KEY_SECRET;
                // 填写Bucket名称，例如examplebucket。
                String bucketName = BUCKET_NAME;

                try {
                    // 创建OSSClient实例。
                    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
                    InputStream inputStream = file.getInputStream();
                    // 创建PutObject请求。
                    //获取文件名称
                    String filename = file.getOriginalFilename();

                    //在文件名称里面添加随机唯一的值
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    filename = uuid + filename;

                    //把文件按照日期进行分类
                    // 2019/11/12/01.jpg
                    //获取当前日期时间
                    String datePath = new DateTime().toString("yyyy/MM/dd");
                    filename = datePath + "/" + filename;

                    ossClient.putObject(bucketName, filename, inputStream);

                    ossClient.shutdown();
                    //把上传之后文件路径返回
                    //需要把上传到阿里云oss路径手动拼接出来
                    String url = "https://" + bucketName + "." + endpoint + "/" + filename;
                    return url;

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
    }
}
