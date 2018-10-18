package com.java8;

import java.util.Optional;


public class OptionalTest {
	
	public static void strIsNull (String target) {
		/*
		//写法一(不允许空)--这个写法不能出来异常
		Optional<String> name = Optional.of(target);
		//如果name为空则抛出空指针
		if (name.isPresent()) {
			System.out.println(name.get());
		} else {
			//如果为null会抛出空指针
		}
		
        //写法二
		Optional<String> str = Optional.ofNullable(target);
		if (str.isPresent()) {
			System.out.println(str.get());
		} else {
			System.out.println("str is null");
		}
		*/
		//针对对象
		Task task = new Task();
		task = null;
		Optional<Task> str = Optional.ofNullable(task);
		str.orElse(new Task()); //如果不存在则创建一个
		
		str.ifPresent(t ->{
			System.out.println("do sth");
			System.out.println("do sth");
		});
		
		
	}

	public static void main(String[] args) {
		OptionalTest.strIsNull(null);
		

	}
	
	
	

}
