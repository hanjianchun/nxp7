package com.hjc.section01;

import com.hjc.section01.config.MyApplicationContext;
import com.hjc.section01.controller.TestController;
import lombok.extern.slf4j.Slf4j;

/**
 * @version v1.0
 * @Description: 第一节课作业
 * @Author: hanjianchun@shouqiev.com
 * @Date: 2020-09-22 16:30
 */
@Slf4j
public class Section01Application {

    public static void main(String[] args){
        MyApplicationContext applicationContext = new MyApplicationContext();
        TestController loginController = (TestController) applicationContext.getIocBean("TestController");
        String check = loginController.check("张三");
        log.info("执行的结果为：{}",check);
    }

}
