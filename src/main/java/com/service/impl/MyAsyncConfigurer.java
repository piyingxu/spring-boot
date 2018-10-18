/*package com.service.impl;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

*//**
 * 通过实现AsyncConfigurer自定义异常线程池，包含异常处理
 * 
 *//*
@Service
public class MyAsyncConfigurer implements AsyncConfigurer {
	
  private static final Logger log = LoggerFactory.getLogger(MyAsyncConfigurer.class);
 
  @Override
  public Executor getAsyncExecutor() {
	 log.info("===========线程池初始化开始===========");
     ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor(); 
     threadPool.setThreadNamePrefix("my-thread");  //线程的名称的前缀 
     threadPool.setCorePoolSize(1);  //核心线程数
     threadPool.setMaxPoolSize(2);  //线程池的大小
     threadPool.setQueueCapacity(1); //队列长度  任务队列容量（阻塞队列）
     threadPool.setKeepAliveSeconds(120); //线程保活时间（单位秒）
     threadPool.setRejectedExecutionHandler(new  RejectedExecutionHandler() {
	     @Override
	     public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
	    	 log.warn("===========线程池任务已满,不再接受新的任务===========");
	     }
     });  
     threadPool.initialize();
     log.info("===========线程池初始化结束===========threadPool：{}", JSONObject.toJSONString(threadPool));
     return threadPool; 
  }
 
  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
     return new MyAsyncExceptionHandler(); 
  }
 
  *//**
   * 自定义异常处理类
   * @author hry
   *
   *//*
  class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler { 
     @Override
     public void handleUncaughtException(Throwable throwable, Method method, Object... obj) { 
       log.info("Exception message - " + throwable.getMessage()); 
       log.info("Method name - " + method.getName()); 
       for (Object param : obj) { 
          log.info("Parameter value - " + param); 
       } 
     } 
  } 
 
}*/