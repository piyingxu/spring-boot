package com.java8.lambda;

import com.java8.lambda.functionalInterface.HelloInterFace;
import com.java8.lambda.functionalInterface.HelloInterfaceOld;

public class Test {
	
	public static String doSth (HelloInterfaceOld old, String user, String msg) {
		return old.getMax(user, msg);
	}

	public static void main(String[] args) {
		
		//jdk8 之前----匿名内部类
		String ret = Test.doSth(new HelloInterfaceOld() {
			@Override
			public String say(String a, String b) {
				a = a + ",i am xp";
				b = b + ",i am yq";
				String c = a + "<======>" + b;
				HelloInterFace.fun();
				return c;
			}
		}, "yq", "xp");
		System.out.println(ret);
		
		//jdk8之后===============================================
		/*
		(params) -> expression
		(params) -> statement
		(params) -> { statements }
		*/
		HelloInterFace hello = (a, b) -> {
			a = a + ",i am xp";
			b = b + ",i am yq";
			String c = a + "<======>" + b;
			HelloInterFace.fun();
			return c;
		};
		//String ret = hello.say("yq", "xp");
		ret = hello.getMax("yq", "xp");
		System.out.println(ret);
	}

}
