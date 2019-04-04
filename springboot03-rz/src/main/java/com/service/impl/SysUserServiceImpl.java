package com.service.impl;

import com.entity.SysUser;
import com.entity.SysUserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.SysUserMapper;
import com.service.SysUserService;
import com.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public List<SysUser> findAll() {
        List<SysUser> list = sysUserMapper.selectByExample(null);
        return list;
    }

    @Override
    public R login(SysUser sysUser) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(sysUser.getUsername());
        List<SysUser> list = sysUserMapper.selectByExample(example);
        if(list==null||list.size()==0){
            return R.error("账号不存在");
        }
        SysUser u = list.get(0);
        if (!u.getPassword().equals(sysUser.getPassword())){
            return R.error("密码错误");
        }if (u.getStatus()==0){
            return R.error("账号被冻结");
        }
        return R.ok().put("u",u);
    }

    @Override
    public ResultData findByPage(Pager pager, String search, Sorter sorter) {

        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        SysUserExample sysUserExample = new SysUserExample();
        if(sorter!=null&& StringUtils.isNotEmpty(sorter.getSort())){
            sysUserExample.setOrderByClause("user_id"+sorter.getOrder());
        }
        SysUserExample.Criteria criteria = sysUserExample.createCriteria();
        if (search!=null&&!"".equals(search)){
            criteria.andUsernameLike("%"+search+"%");
        }
        List<SysUser> list = sysUserMapper.selectByExample(sysUserExample);
        PageInfo info = new PageInfo(list);
        ResultData resultData = new ResultData(info.getTotal(),info.getList());
        return resultData;
    }

    @Override
    public SysUser findByName(String name) {
        SysUserExample sysUserExample = new SysUserExample();
        SysUserExample.Criteria criteria = sysUserExample.createCriteria();
        criteria.andUsernameEqualTo(name);
        List<SysUser> list = sysUserMapper.selectByExample(sysUserExample);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public R updatepwd(SysUser sysUser) {
        int i = sysUserMapper.updateByPrimaryKey(sysUser);

        return i>0?R.ok("修改成功"):R.error("修改失败");
    }

    @Override
    public R findPieData() {
        List<Map<String, Object>> list = sysUserMapper.findPieData();
        List list1 = new ArrayList();
        for (Map<String, Object> map : list) {
            String name = map.get("name")+"";
            list1.add(name);
        }
        return R.ok().put("pieData",list).put("legendData",list1);
    }
}
