package com.service;

import com.entity.ScheduleJob;
import com.utils.Pager;
import com.utils.R;
import com.utils.ResultData;

import java.util.List;

public interface ScheduleService {
    public ResultData scheduleList(Pager pager,String search);
    public R save(ScheduleJob scheduleJob);
    public R update(ScheduleJob scheduleJob);
    public R delete(List<Long> jobIds);
    public R scheduleInfo(long id);

    //暂停
    public R pause(List<Long> jobIds);
    //恢复
    public R resume(List<Long> jobIds);
    //立即执行
    public R run(List<Long> jobIds);
}
