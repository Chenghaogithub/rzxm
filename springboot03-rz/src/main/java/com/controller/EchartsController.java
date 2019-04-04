package com.controller;

import com.service.SysUserService;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchartsController {
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/sys/echarts/pie")
    public R pie(){
        return sysUserService.findPieData();
    }
}
