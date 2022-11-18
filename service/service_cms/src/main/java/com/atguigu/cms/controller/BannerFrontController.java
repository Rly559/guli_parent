package com.atguigu.cms.controller;

import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.service.CrmBannerService;
import com.atguigu.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: LLY
 * @Date: 2022/11/10 18:07
 */
@RestController
@RequestMapping("/cmsservice/bannerFront")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
//    @Cacheable(key = "'selectIndexList'",value = "banner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.list(null);
        return R.ok().data("bannerList", list);
    }
}
