package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/10/14 19:46
 */
@Data
public class ChapterVo {
    private String id;

    private String title;

    //表示小节
    private List<VideoVo> children = new ArrayList<>();
}
