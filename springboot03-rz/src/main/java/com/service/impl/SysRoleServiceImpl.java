package com.service.impl;

import com.entity.SysRole;
import com.entity.SysRoleExample;
import com.entity.SysUser;
import com.entity.SysUserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.SysRoleMapper;
import com.service.SysRoleService;
import com.utils.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**

 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    //注入mapper
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<String> findRolesByUserId(long userId) {
        return sysRoleMapper.findRolesByUserId(userId);
    }

    @Override
    public ResultData findByPage(Pager pager, String search, Sorter sorter) {
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        SysRoleExample sysRoleExample = new SysRoleExample();
        if(sorter!=null&& StringUtils.isNotEmpty(sorter.getSort())){
            sysRoleExample.setOrderByClause("role_id"+sorter.getOrder());
        }
        SysRoleExample.Criteria criteria = sysRoleExample.createCriteria();
        if (search!=null&&!"".equals(search)){
            criteria.andRoleNameLike("%"+search+"%");
        }
        List<SysRole> list1 = sysRoleMapper.selectByExample(sysRoleExample);
        for (SysRole role : list1) {
            System.out.println("集合的长度为"+list1.size());
            System.out.println("角色："+role);
        }
        PageInfo info = new PageInfo(list1);
        ResultData resultData1 = new ResultData(info.getTotal(),info.getList());
        return resultData1;

    }

    @Override
    public R save(SysRole sysRole) {
        int i = sysRoleMapper.insert(sysRole);
        return i>0?R.ok():R.error("添加失败");
    }

    @Override
    public R roleInfo(long id) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
        return R.ok().put("sysRole",sysRole);
    }
}
