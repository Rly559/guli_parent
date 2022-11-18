package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/10/26 16:38
 */
@Data
public class CourseQuery {
    @ApiModelProperty(value = "课程名称,模糊查询")
    private String title;

    @ApiModelProperty(value = "状态 Normal已发布 Draft未发布")
    private String status;
}
