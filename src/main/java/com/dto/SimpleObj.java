package com.dto;

public class SimpleObj {
	
    private int a;
    
    private int b;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}
	
	public void getName () {
		System.out.println("I am SimpleObj,class address=" + this.toString());
	}
    
}
