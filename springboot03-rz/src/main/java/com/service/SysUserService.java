package com.service;

import com.entity.SysUser;
import com.utils.Pager;
import com.utils.R;
import com.utils.ResultData;
import com.utils.Sorter;

import java.util.List;

public interface SysUserService {
    public List<SysUser> findAll();
    public R login(SysUser sysUser);
    public ResultData findByPage(Pager pager, String search, Sorter sorter);
    public SysUser findByName(String name);
    public R updatepwd(SysUser sysUser);
    public R findPieData();
}
