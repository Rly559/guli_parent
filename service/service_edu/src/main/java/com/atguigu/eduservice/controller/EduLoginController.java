package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/9/23 15:35
 */
@Api("登录方法")
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin//解决跨域
public class EduLoginController {
    //login
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","admin").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
