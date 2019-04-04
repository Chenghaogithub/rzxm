package com.quartz.task;

import org.springframework.stereotype.Component;

/**

 * @Time: 上午9:10
 */
@Component(value = "backUpDB")//spring容器中
public class BackUpDBTask {

    public void backUp(){
        System.out.println("数据库备份！");

    }

}
