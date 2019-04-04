package com.controller;

import com.entity.SysMenu;
import com.service.SysMenuService;
import com.utils.R;
import com.utils.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;
    @RequiresPermissions("sys:menu:list")
    @RequestMapping("/sys/menu/list")
    public ResultData menuList(int limit,int offset,String search,String sort,String order){
        return sysMenuService.findByPage(limit,offset,search,sort,order);
    }
    @RequiresPermissions("sys:menu:delete")
    @RequestMapping("sys/menu/del")
    public R del(  @RequestBody  List<Long> ids){
        return sysMenuService.del(ids);
    }
    @RequiresPermissions("sys:menu:select")
    @RequestMapping("sys/menu/select")
    public R select(){
        return sysMenuService.selectMenu();
    }
    @RequiresPermissions("sys:menu:save")
    @RequestMapping("sys/menu/save")
    public R save(@RequestBody SysMenu sysMenu){

        return sysMenuService.save(sysMenu);
    }
    @RequiresPermissions("sys:menu:select")
    @RequestMapping("sys/menu/info/{menuId}")
    public R findMenu(@PathVariable long menuId){
        return sysMenuService.findMenu(menuId);
    }
    @RequiresPermissions("sys:menu:update")
    @RequestMapping("sys/menu/update")
    public R update(@RequestBody SysMenu sysMenu){
        return sysMenuService.update(sysMenu);
    }
    /*  "menuList": [{
    "menuId": 1,
    "parentId": 0,
    "parentName": null,
    "name": "系统管理",
    "url": null,
    "perms": null,
    "type": 0,
    "icon": "fa fa-cog",
    "orderNum": 0,
    "open": null,
    "list": [{
      "menuId": 2,
      "parentId": 1,
      "parentName": null,
      "name": "用户管理",
      "url": "sys/user.html",
      "perms": null,
      "type": 1,
      "icon": "fa fa-user",
      "orderNum": 1,
      "open": null,
      "list": null
    },*/

    @RequestMapping("sys/menu/user")
    public R menuUser(){
        return  sysMenuService.findUserMenu();
    }
}
