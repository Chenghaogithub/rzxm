package com.quartz.task;

import org.springframework.stereotype.Component;

/**

 * @Time: 上午9:10
 */
@Component(value = "unLockAccount")//spring容器中
public class UnLockAccount {

    public void unLock(){
        System.out.println("解封账户！");

    }

}
