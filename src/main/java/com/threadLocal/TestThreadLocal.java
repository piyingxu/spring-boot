package com.threadLocal;

public class TestThreadLocal {

	private static ThreadLocal<Person> pesonal = new ThreadLocal<Person>() {
		@Override
        protected Person initialValue() {
			Person person = new Person();
			String threadName = Thread.currentThread().getName();
			person.setName(threadName);
            return new Person();
        }
	};
	
	public static void getPerson (String threadName) {
		Person person =	pesonal.get();
		System.out.println(threadName + "------" +person);
	}
	

	public static void main(String[] args) {
		for (int i=0;i<5;i++) {
			new Thread(()-> {
				Thread t = Thread.currentThread(); 
				getPerson(t.getName());
	        }).start();
		}
	}

}
