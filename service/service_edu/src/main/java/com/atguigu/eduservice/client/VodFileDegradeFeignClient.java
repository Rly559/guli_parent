package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/11/8 20:51
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R deleteVideoByVideoId(String videoId) {
        return R.error().message("time out");
    }
}
