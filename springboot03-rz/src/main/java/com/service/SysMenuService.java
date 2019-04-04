package com.service;

import com.entity.SysMenu;
import com.utils.R;
import com.utils.ResultData;

import java.util.List;

public interface SysMenuService {
    public ResultData findByPage(int limit,int offset);
    public ResultData findByPage(int limit,int offset,String search);
    public ResultData findByPage(int limit,int offset,String search,String sort,String order);
    public R del(List<Long> ids );
    public R selectMenu();
    public R save(SysMenu sysMenu);
    public R findMenu(long menuId);
    public R update(SysMenu sysMenu);
    public List<String>  findPermsByUserId(long userId);
    public  R findUserMenu();
}
