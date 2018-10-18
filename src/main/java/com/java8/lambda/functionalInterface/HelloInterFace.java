package com.java8.lambda.functionalInterface;

@FunctionalInterface
public interface HelloInterFace {
	
    public String say (String user,String msg); //唯一的抽象方法，有且仅有一个 
    
    public boolean equals(Object obj);       //允许从Object类继承方法
    
    default String getMax (String user,String msg) {  //允许使用default修饰
    	
    	return "getMax is doing!!" + say(user, msg);
    }
	
    default void other (String str) {        //允许使用default修饰
    	System.out.println("yes i am other");
    }
	
    static void fun () {                     //允许使用静态修饰
    	System.out.println("yes i am static");
    }
}
