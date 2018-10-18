package com.java8.lambda.functionalInterface;

public interface HelloInterfaceOld {
    
	public String say (String user,String msg);
	    
    public boolean equals(Object obj);    
    
    default String getMax (String user,String msg) { 
    	
    	return "getMax is doing!!" + say(user, msg);
    }
	
    default void other (String str) {        
    	System.out.println("yes i am other");
    }
	
    static void fun () {                     
    	System.out.println("yes i am static");
    }
}
