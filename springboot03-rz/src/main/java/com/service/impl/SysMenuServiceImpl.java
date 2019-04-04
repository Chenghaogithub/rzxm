package com.service.impl;

import com.entity.SysMenu;
import com.entity.SysMenuExample;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.SysMenuMapper;
import com.service.SysMenuService;
import com.utils.R;
import com.utils.ResultData;
import com.utils.ShiroUtils;
import com.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public ResultData findByPage(int limit, int offset) {
        PageHelper.offsetPage(offset,limit);
        List<SysMenu> list = sysMenuMapper.selectByExample(null);
        PageInfo pageInfo = new PageInfo(list);
        long total = pageInfo.getTotal();
        List<SysMenu> list1 = pageInfo.getList();
        ResultData resultData = new ResultData(total,list1);
        return resultData;
    }
    /*@RequiresPermissions("sys:menu:select")*/
    @Override
    public ResultData findByPage(int limit, int offset, String search) {
        PageHelper.offsetPage(offset,limit);

        SysMenuExample example = null;
        if (search!=null&&!"".equals(search)){
            example= new SysMenuExample();
            SysMenuExample.Criteria criteria = example.createCriteria();
            criteria.andNameLike("%"+search+"%");
        }
        List<SysMenu> list  =  sysMenuMapper.selectByExample(example);


        PageInfo info = new PageInfo(list);

        long total = info.getTotal();
        List<SysMenu> list1 = info.getList();

        ResultData resultData = new ResultData(total,list1);

        return resultData;
    }
    //@RequiresPermissions("sys:menu:list")
    @Override
    public ResultData findByPage(int limit, int offset, String search, String sort, String order) {

        PageHelper.offsetPage(offset,limit);

        SysMenuExample example =  example= new SysMenuExample();

        SysMenuExample.Criteria criteria = example.createCriteria();
        if (search!=null&&!"".equals(search)){
            criteria.andNameLike("%"+search+"%");
        }
        //  order by ${orderByClause}   :${orderByClause}
        //sys/menu/list?sort=menuId&order=desc&limit=10&offset=0
        if (sort!=null&&sort.equals("menuId")){
            sort = "menu_id";
        }
        example.setOrderByClause(sort+" "+order);

        List<SysMenu> list  =  sysMenuMapper.selectByExample(example);


        PageInfo info = new PageInfo(list);

        long total = info.getTotal();
        List<SysMenu> list1 = info.getList();

        ResultData resultData = new ResultData(total,list1);

        return resultData;
    }
    /*@RequiresPermissions("sys:menu:delete")*/
    @Override
    public R del(List<Long> ids) {
        SysMenuExample sysMenuExample = new SysMenuExample();
        SysMenuExample.Criteria criteria = sysMenuExample.createCriteria();
        for (Long id:
             ids) {
            if (id<31){
                return R.error("危险操作！");
            }

        }
        criteria.andMenuIdIn(ids);
        int i = sysMenuMapper.deleteByExample(sysMenuExample);
        if (i>0){
            return R.ok("删除成功！");
        }

        return R.error("删除失败！");
    }
    /*@RequiresPermissions("sys:menu:select")*/
    @Override
    public R selectMenu() {

        List<SysMenu> list = sysMenuMapper.findMenuNotButton();
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0l);
        sysMenu.setParentId(-1l);
        sysMenu.setName("顶级目录");
        sysMenu.setOrderNum(0);
        list.add(sysMenu);
        return R.ok().put("menuList",list);

    }
    /*@RequiresPermissions("sys:menu:save")*/
    public R save(SysMenu sysMenu){
        int i = sysMenuMapper.insert(sysMenu);
        return i>0?R.ok():R.error("新增失败");

    }

    @Override
    public R findMenu(long menuId) {
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(menuId);

        return R.ok().put("menu",sysMenu);
    }

    @Override
    public R update(SysMenu sysMenu) {
        int i = sysMenuMapper.updateByPrimaryKey(sysMenu);
        return i>0?R.ok():R.error("新增失败！");
    }

    @Override
    public List<String> findPermsByUserId(long userId) {
        List<String> list = sysMenuMapper.findPermsByUserId(userId);
        Set set = new HashSet<String>();
        for (String s : list) {
            if (StringUtils.isNotEmpty(s)){
                //"sys:user:list,sys:user:info,sys:user:select"
                //"sys:user:list"
                String ss[] =  s.split(",");
                for (String s1 : ss) {
                    set.add(s1);
                }
            }
        }
        List<String> newList = new ArrayList<>();
        newList.addAll(set);

        return newList;
    }

    @Override
    public R findUserMenu() {
        long userId = ShiroUtils.getUserId();
        //目录
        List<SysMenu> dir = sysMenuMapper.findDir(userId);
        for (SysMenu sysMenu : dir) {
            //查询菜单
            List<SysMenu> menuList =   sysMenuMapper.findMenu(sysMenu.getMenuId(),userId);
            sysMenu.setList(menuList);
        }

        List<String> permissions =this. findPermsByUserId(userId);

        return R.ok().put("menuList",dir).put("permissions",permissions);
    }

}
