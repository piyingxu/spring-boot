package com.annotation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class SpringBeanLife implements InitializingBean, DisposableBean {
	
	public SpringBeanLife() {
		System.out.println("SpringBeanLife Construct Fun is doing");
	}
	
	@PostConstruct //构造方法调用之后调用
	public void TestPostConstruct() {
		System.out.println("SpringBeanLife TestPostConstruct Fun is doing");
	}
	
	@PreDestroy   //bean销毁方法调用之前调用
	public void TestPreDestroy() {
		System.out.println("SpringBeanLife TestPreDestroy Fun is doing");
	}
	
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
