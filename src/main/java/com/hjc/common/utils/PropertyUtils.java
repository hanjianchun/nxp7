package com.hjc.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @version v1.0
 * @Description: TODO(一句话描述该类的信息)
 * @Author: hanjianchun@shouqiev.com
 * @Date: 2020-09-22 19:03
 */
public class PropertyUtils {

    /**
     * 效率低，为了演示
     * @param key
     */
    public static String getResourceByKey(String key,String section){
        Properties properties = new Properties();
        InputStream in = PropertyUtils.class.getClassLoader().getResourceAsStream(section+"/application.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

}
