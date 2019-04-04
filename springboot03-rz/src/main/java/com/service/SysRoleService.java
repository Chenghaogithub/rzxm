package com.service;

import com.entity.SysRole;
import com.utils.Pager;
import com.utils.R;
import com.utils.ResultData;
import com.utils.Sorter;

import java.util.List;

/**
 * //
 */
public interface SysRoleService {

    public List<String> findRolesByUserId(long userId);
    public ResultData findByPage(Pager pager, String search, Sorter sorter);
    public R save(SysRole sysRole);
    public R roleInfo(long id);

}
