package com.hjc.section02;

import com.hjc.section02.set.InstrumentedHashSet;
import lombok.extern.slf4j.Slf4j;

/**
 * @version v1.0
 * @Description: 第二节课启动类
 * @Author: hanjianchun@shouqiev.com
 * @Date: 2020-09-22 16:31
 */
@Slf4j
public class Section02Application {

    public static void main(String[] args) {
        InstrumentedHashSet instrumentedHashSet = new InstrumentedHashSet();
        instrumentedHashSet.add("你好");
        log.info(instrumentedHashSet.size()+"");

        instrumentedHashSet.clear();

        log.info(instrumentedHashSet.size()+"");

    }

}
