package com.hjc.jdk;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version v1.0
 * @Description: TODO(一句话描述该类的信息)
 * @Author: hanjianchun@shouqiev.com
 * @Date: 2020-09-23 15:16
 */
@Slf4j
public class Test {

    public static void main(String[] args) throws InterruptedException {
        Map<String,String> map = new HashMap<>();
        map.put("12","12");
        System.out.println(map);

        Object key = "123";
        log.info("1-{}",key.hashCode());
        log.info("2-{}",key.hashCode() >>> 16);
        log.info("3-{}",key.hashCode() ^ 2);
        log.info("4-{}",10 ^ 3);
        int h;
        System.out.println(key == null ? 0 : (h = key.hashCode()) ^ (h >>> 16));

        TestEntity testEntity = new TestEntity();
        log.info("hash1-{}",testEntity.hashCode());
        testEntity.setAge(14);
        log.info("hash2-{}",testEntity.hashCode());

        TestEntity testEntity1 = new TestEntity();
        log.info("hash3-{}",testEntity1.hashCode());
        testEntity1.setAge(14);


        Executors.newFixedThreadPool(1);

        /**
         * 各参数含义
         * corePoolSize    : 线程池中常驻的线程数量。核心线程数，默认情况下核心线程会一直存活，即使处于闲置状态也不会
         *                   受存活时间 keepAliveTime 的限制，除非将 allowCoreThreadTimeOut 设置为 true。
         * maximumPoolSize : 线程池所能容纳的最大线程数。超过这个数的线程将被阻塞。当任务队列为没有设置大小的
         *                         LinkedBlockingQueue时，这个值无效。
         * keepAliveTime   : 当线程数量多于 corePoolSize 时，空闲线程的存活时长，超过这个时间就会被回收
         * unit            : keepAliveTime 的时间单位
         * workQueue       : 存放待处理任务的队列
         * threadFactory   : 线程工厂
         * handler         : 拒绝策略，拒绝无法接收添加的任务
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100,
        110,
        1,
        TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(2),new NameTreadFactory(),new MyIgnorePolicy());

//        for(int i = 0;i<200;i++) {
//            MyTask myTask = new MyTask(i+"");
//            threadPoolExecutor.execute(myTask);
//            if(i > 120) Thread.sleep(1000);
//        }


        log.info("异或运算 {}",Integer.toString(0b1111 ^ 0b0001,2));
        log.info("负数计算 {}",Integer.toString(-128,2));


        log.info("\n{}", ClassLayout.parseInstance(testEntity).toPrintable());


    }
}
class  MyIgnorePolicy implements RejectedExecutionHandler {

    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        doLog(r, e);
    }

    private void doLog(Runnable r, ThreadPoolExecutor e) {
        // 可做日志记录等
        System.err.println( r.toString() + " rejected");
//          System.out.println("completedTaskCount: " + e.getCompletedTaskCount());
    }
}

class NameTreadFactory implements ThreadFactory {

    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
        System.out.println(t.getName() + " has been created");
        return t;
    }
}

class MyTask implements Runnable {
    private String name;

    public MyTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.toString() + " is running!");
            Thread.sleep(3000); //让任务执行慢点
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MyTask [name=" + name + "]";
    }
}

class TestEntity{
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}
