package com.controller;

import com.utils.R;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
12:01
 */

@RestController
public class DelController {

    @Resource
    Scheduler scheduler;

    @RequestMapping("/del")
    public R  del(String  jobId){
        try{
            JobKey jobKey = JobKey.jobKey(jobId);
            //删除定时任务
            scheduler.deleteJob(jobKey);

            return R.ok();
        }catch(Exception e){
            e.printStackTrace();
        }

        return R.error();
    }


}
