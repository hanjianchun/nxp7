package com.hjc.section01.config;

import com.hjc.common.annotation.NXAutowired;
import com.hjc.common.annotation.NXController;
import com.hjc.common.annotation.NXService;
import com.hjc.common.annotation.NXValue;
import com.hjc.common.utils.ClassUtils;
import com.hjc.common.utils.PropertyUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version v1.0
 * @Description: 自定义容器
 * @Author: hanjianchun@shouqiev.com
 * @Date: 2020-09-22 16:50
 */
@Slf4j
public class MyApplicationContext {

    private ConcurrentHashMap<String,Object> contextHashMap = new ConcurrentHashMap<String, Object>();

    public MyApplicationContext() {
        init();
    }

    private void init(){
        String pkg = PropertyUtils.getResourceByKey("component.scan","section01");
        List<String> classes = ClassUtils.getClassesFormPackage(pkg);
        for(String classStr : classes){
            try {
                Class clazz = Class.forName(classStr);
                Annotation[] annotaions = clazz.getAnnotations();
                for(Annotation annotation : annotaions){
                    if(annotation instanceof NXController){
                        String name = classStr.substring(classStr.lastIndexOf(".") + 1,classStr.length());
                        contextHashMap.put(name,clazz.newInstance());
                    }else if(annotation instanceof NXService){
                        Class<?> interfaces[] = clazz.getInterfaces();
                        for (Class<?> inter : interfaces) {
                            String name = inter.getName().substring(inter.getName().lastIndexOf(".") + 1,inter.getName().length());
                            contextHashMap.put(name,clazz.newInstance());
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                log.error("错误信息：{}",e.getMessage());
            } catch (IllegalAccessException e) {
                log.error("错误信息：{}",e.getMessage());
            } catch (InstantiationException e) {
                log.error("错误信息：{}",e.getMessage());
            }
        }
        for(String classStr : classes) {
            Class clazz = null;
            try {
                clazz = Class.forName(classStr);
                Annotation[] annotaions = clazz.getAnnotations();
                for (Annotation annotation : annotaions) {
                    if (annotation instanceof NXController) {
                        String name = classStr.substring(classStr.lastIndexOf(".") + 1,classStr.length());
                        Object obj = contextHashMap.get(name);
                        Field[] fields = clazz.getDeclaredFields();
                        for (Field field : fields) {
                            Annotation[] annotations = field.getDeclaredAnnotations();
                            for (Annotation filedAnnotation : annotations) {
                                if (filedAnnotation instanceof NXAutowired) {
                                    NXAutowired nxAutowired = (NXAutowired) filedAnnotation;
                                    field.setAccessible(true);
                                    field.set(obj, contextHashMap.get(nxAutowired.value()));
                                    field.setAccessible(false);
                                }else if(filedAnnotation instanceof NXValue){
                                    NXValue nxValue = (NXValue) filedAnnotation;
                                    String key = nxValue.value();
                                    String value = PropertyUtils.getResourceByKey(key,"section01");
                                    field.setAccessible(true);
                                    field.set(obj,value);
                                    field.setAccessible(false);
                                }
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                log.error("错误信息：{}",e.getMessage());
            } catch (IllegalAccessException e) {
                log.error("错误信息：{}",e.getMessage());
            }
        }

        log.info("实例化后为：{}",contextHashMap.toString());
    }

    /**
     * 根据key获取bean
     * @param key
     * @return
     */
    public Object getIocBean(String key) {
        return contextHashMap.get(key);
    }
}
