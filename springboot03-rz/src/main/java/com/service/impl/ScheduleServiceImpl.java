package com.service.impl;

import com.entity.ScheduleJob;
import com.entity.ScheduleJobExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.ScheduleJobMapper;
import com.service.ScheduleService;
import com.utils.*;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    @Resource
    private Scheduler scheduler;
    @Override
    public ResultData scheduleList(Pager pager, String search) {
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        ScheduleJobExample example = new ScheduleJobExample();
        if (StringUtils.isNotEmpty(search)){
            ScheduleJobExample.Criteria criteria = example.createCriteria();
            criteria.andBeanNameLike("%"+search+"%");
        }
        List<ScheduleJob> list = scheduleJobMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        ResultData resultData = new ResultData(pageInfo.getTotal(),pageInfo.getList());
        return resultData;
    }

    @Override
    public R save(ScheduleJob scheduleJob) {
        scheduleJob.setStatus((byte)0);
        scheduleJob.setCreateTime(new Date());
        int i = scheduleJobMapper.insert(scheduleJob);
        ScheduleUtils.createScheduleTask(scheduler,scheduleJob);
        return i>0?R.ok():R.error();
    }

    @Override
    public R update(ScheduleJob scheduleJob) {
        int i = scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
        ScheduleUtils.updateScheduleTask(scheduler,scheduleJob);
        return i>0?R.ok():R.error();
    }

    @Override
    public R delete(List<Long> jobIds) {
        ScheduleJobExample example = new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria =  example.createCriteria();
        criteria.andJobIdIn(jobIds);
        int i = scheduleJobMapper.deleteByExample(example);
        //删除真正的任务
        for (Long jobId : jobIds) {
            ScheduleUtils.deleteScheduleTask(scheduler,jobId);
        }
        return i>0?R.ok():R.error();

    }

    @Override
    public R scheduleInfo(long id) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(id);
        return R.ok().put("scheduleJob",scheduleJob);
    }

    @Override
    public R pause(List<Long> jobIds) {
        ScheduleJobExample example = new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria = example.createCriteria();
        criteria.andJobIdIn(jobIds);
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setStatus(SysConstant.ScheduleStatus.PAUSE.getValue());
        int i = scheduleJobMapper.updateByExampleSelective(scheduleJob,example);
        //判断当前用户状态.
        for (Long jobId : jobIds) {
            ScheduleUtils.pause(scheduler,jobId);
        }

        return i>0?R.ok():R.error();

    }

    @Override
    public R resume(List<Long> jobIds) {
        ScheduleJobExample example = new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria = example.createCriteria();
        criteria.andJobIdIn(jobIds);
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());
        int i = scheduleJobMapper.updateByExampleSelective(scheduleJob,example);
        //判断当前用户状态.
        for (Long jobId : jobIds) {
            ScheduleUtils.resume(scheduler,jobId);
        }

        return i>0?R.ok():R.error();
    }
    @Override
    public R run(List<Long> jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.runOnce(scheduler,jobId);
        }
        return R.ok();
    }
}
