package com.quartz;

import com.alibaba.fastjson.JSON;
import com.entity.ScheduleJob;
import com.entity.ScheduleJobLog;
import com.utils.SpringContextUtils;
import com.utils.SysConstant;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.InvocationTargetException;

/**

 */
public class MyJob  implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        String json = (String) context.getJobDetail().getJobDataMap().get(SysConstant.SCHEDULE_DATA_KEY);
        //{"beanName":"testTask","cronExpression":"* * * * * ?","jobId":33,"methodName":"TEST","params":"1","remark":"测试"}
        try {
            System.out.println(json);
            //json转对象 JSON.parseObject
            ScheduleJob scheduleJob = JSON.parseObject(json, ScheduleJob.class);
            //拿到beanName  method
            String beanName = scheduleJob.getBeanName();
            String method = scheduleJob.getMethodName();
            //根据beanname拿到对象？ 装饰者模式
            Object object = SpringContextUtils.getBean(beanName);
            //已知方法和object对象名如何调用方法？  反射
            object.getClass().getDeclaredMethod(method).invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
