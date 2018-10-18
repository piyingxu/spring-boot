package com.annotation;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TestAnnoFour {
	
	public void test () {
		System.out.println(this.toString());
	}
	
	public int getNum () {
		return 1;
	}
	
	
}

