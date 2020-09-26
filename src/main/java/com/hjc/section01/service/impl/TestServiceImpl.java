package com.hjc.section01.service.impl;

import com.hjc.common.annotation.NXService;
import com.hjc.section01.service.TestService;

/**
 * @version v1.0
 * @Description: 测试service
 * @Author: hanjianchun@shouqiev.com
 * @Date: 2020-09-22 18:05
 */
@NXService("TestService")
public class TestServiceImpl implements TestService {

    @Override
    public String hello(String name) {
        return name;
    }
}
