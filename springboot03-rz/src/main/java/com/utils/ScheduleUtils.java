package com.utils;

import com.alibaba.fastjson.JSON;
import com.entity.ScheduleJob;
import com.exception.RZException;
import com.quartz.MyJob;
import org.quartz.*;

public class ScheduleUtils {
    //新增
    public  static void createScheduleTask(Scheduler scheduler,ScheduleJob scheduleJob){//QuartzConfig
        try{

            JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("myJob_"+scheduleJob.getJobId()).build();
            //如何向任务类传参

            String json = JSON.toJSONString(scheduleJob);
            jobDetail.getJobDataMap().put(SysConstant.SCHEDULE_DATA_KEY,json);

            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("myTrigger_"+scheduleJob.getJobId()).
                    withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())).build();

            scheduler.scheduleJob(jobDetail,cronTrigger);

        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RZException("创建定时任务失败");
        }
    }
    //删除
    public  static void deleteScheduleTask(Scheduler scheduler,long jobId){//QuartzConfig

        try {
            JobKey jobKey = JobKey.jobKey("myJob_"+jobId);
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            throw new RZException("删除定时任务失败");
        }
    }

    //修改
    public static void updateScheduleTask(Scheduler scheduler,ScheduleJob scheduleJob){

        //TriggerKey
        TriggerKey triggerKey = TriggerKey.triggerKey("myTrigger_"+scheduleJob.getJobId());



        try {
            //获得触发器
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //修改表达式
            cronTrigger = cronTrigger.getTriggerBuilder().withSchedule(CronScheduleBuilder.
                    cronSchedule(scheduleJob.getCronExpression())).build();
            //重置触发器
            scheduler.rescheduleJob(triggerKey,cronTrigger);


        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    public  static void pause(Scheduler scheduler,long jobId){
        try{
            JobKey jobKey = JobKey.jobKey("myJob_"+jobId);
            scheduler.pauseJob(jobKey);
            System.out.println("暂停成功！");
        }catch(Exception e){
            throw  new  RZException("暂停任务失败，请联系管理员");
        }
    }

    public  static void resume(Scheduler scheduler,long jobId){
        try{
            JobKey jobKey = JobKey.jobKey("myJob_"+jobId);
            scheduler.resumeJob(jobKey);
        }catch(Exception e){
            throw  new  RZException("暂停任务失败，请联系管理员");
        }
    }

    public  static void runOnce(Scheduler scheduler,long jobId){
        try{
            JobKey jobKey = JobKey.jobKey("myJob_"+jobId);
            scheduler.triggerJob(jobKey);

        }catch(Exception e){
            throw  new  RZException("执行任务失败，请联系管理员");
        }
    }
}
