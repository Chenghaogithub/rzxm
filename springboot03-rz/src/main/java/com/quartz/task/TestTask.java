package com.quartz.task;

import org.springframework.stereotype.Component;

@Component(value = "testTask")
public class TestTask {

    public  void  test(){
        System.out.println("测试--无参");
    }

    public  void  test(String params){
        System.out.println("测试--带参"+params);

    }

}
