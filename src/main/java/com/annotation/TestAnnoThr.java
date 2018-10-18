package com.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.dto.SimpleObj;

@Configuration("piyingxu")
@Lazy(false)
public class TestAnnoThr {
	
	@Bean
	public TestAnnoTwo getBeanTwo () {
		System.out.println("getBeanTwo");
		return new TestAnnoTwo();
	}
	
	@Bean(name = "myBeanTwo")
	public TestAnnoTwo getBeanTwo_other () {  //如果方法定义为public，则其他类可以通过 @Resource(name = "myBeanTwo") 获取到这个对象
		return getBeanTwo();
	}
	
	@Bean
	public SimpleObj getBeanSimpleObj () {
		return new SimpleObj();
	}
	
	
	
	public void test () {
		//测试 @Component与 @Configuration的区别--（结果：testAnnoTwo_2与testAnnoTwo_3是同一个对象）
		TestAnnoTwo testAnnoTwo_2 = getBeanTwo();
		TestAnnoTwo testAnnoTwo_3 = getBeanTwo_other();
		testAnnoTwo_2.getName();
		testAnnoTwo_3.getName();
	}
}
