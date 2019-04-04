package com.controller;

import com.entity.SysRole;
import com.service.SysRoleService;
import com.utils.Pager;
import com.utils.R;
import com.utils.ResultData;
import com.utils.Sorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @RequestMapping("/sys/role/list")
    public ResultData findRoleByPage(Pager pager, String search, Sorter sorter){
        System.out.println("controller已被调用");
        return sysRoleService.findByPage(pager,search,sorter);
    }
    @RequestMapping("/sys/role/save")
    public R save(@RequestBody SysRole sysRole){
        return sysRoleService.save(sysRole);
    }

    @RequestMapping("/sys/role/info/{jobId}")
    public R info(@PathVariable long roleId){
        System.out.println("这个方法被调用了");
        return sysRoleService.roleInfo(roleId);
    }
}
