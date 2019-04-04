package com.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier(value = "druidDatasource") DataSource dataSource){

        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        Properties properties = new Properties();
        properties.setProperty("org.quartz.scheduler.instanceName","MyQuartzScheduler");
        properties.setProperty("org.quartz.scheduler.instanceid","AUTO");
        properties.setProperty("org.quartz.threadPool.class","org.quartz.simpl.SimpleThreadPool");
        properties.setProperty("org.quartz.threadPool.threadCount","10");
        properties.setProperty("org.quartz.jobStore.class","org.quartz.impl.jdbcjobstore.JobStoreTX");
        properties.setProperty("org.quartz.jobStore.tablePrefix","QRTZ_");

        schedulerFactoryBean.setQuartzProperties(properties);

        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setOverwriteExistingJobs(true);//覆盖已知job
        schedulerFactoryBean.setStartupDelay(1);//
        schedulerFactoryBean.setDataSource(dataSource);
        return schedulerFactoryBean;
    }
}
