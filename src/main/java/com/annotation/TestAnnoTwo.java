package com.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/*
singleton：定义bean的范围为每个spring容器一个实例（默认值）
prototype：定义bean可以被多次实例化（使用一次就创建一次）
request： 定义bean的范围是http请求（springMVC中有效）
session： 定义bean的范围是http会话（springMVC中有效）
global-session：定义bean的范围是全局http会话（portlet中有效）
*/
@Component("TestAnnoTwo") //给自己在Spring容器中取个名字
@Scope("singleton") // singleton(单例)、prototype(多例)
@PropertySource("classpath:httpclient.properties")
public class TestAnnoTwo {
	
	@Value("${http.maxTotal}")
    private Integer maxTotal;
    
	public void getName () {
		System.out.println("I am TestAnnoTwo,class address=" + this.toString());
	}
	
	public void maxTotal () {
		System.out.println("Call TestAnnoTwo, maxTotal=" + maxTotal);
	}
	
}
