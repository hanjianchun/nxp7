package com.hjc.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

/**
 * @version v1.0
 * @Description: class操作工具类
 * @Author: hanjianchun@shouqiev.com
 * @Date: 2020-09-22 16:59
 */
@Slf4j
public class ClassUtils {

    /**
     * 通过path获取所有的类名
     * @param packageName
     * @return
     */
    public static List<String> getClassesFormPackage(String packageName){
        List<String> list = new ArrayList<String>();
        Enumeration<URL> urls;
        try {
            urls = Thread.currentThread().getContextClassLoader().getResources(packageName.replace(".","/"));
            List<File> classFiles = new ArrayList<>();
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                if(url != null){
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    classFiles.add(new File(filePath));
                }
            }
            for(File file : classFiles){
                list.addAll(findClasses(file,packageName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static List<String> findClasses(File directory, String packageName){
        List<String> classes = new ArrayList<String>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                log.info("读取到的class文件完整路径为：{}",packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
                classes.add(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
            }
        }
        return classes;
    }
}
