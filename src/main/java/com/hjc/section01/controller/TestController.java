package com.hjc.section01.controller;

import com.hjc.common.annotation.NXAutowired;
import com.hjc.common.annotation.NXController;
import com.hjc.common.annotation.NXValue;
import com.hjc.section01.service.TestService;
import lombok.extern.slf4j.Slf4j;

/**
 * @version v1.0
 * @Description: 测试controller
 * @Author: hanjianchun@shouqiev.com
 * @Date: 2020-09-22 16:43
 */
@Slf4j
@NXController
public class TestController {

    @NXAutowired(value = "TestService")
    private TestService testService;

    @NXValue("component.scan")
    private String scanPackage;

    public String check(String name){
        return "你好,,"+testService.hello(name) + ",,扫描包目录：" + scanPackage;
    }
}
