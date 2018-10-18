package com.java8;

public class TaskSon {
	
   private int id;
   
   private int type;
   
   private String title;
   
   private String desc;
   
   public TaskSon(int id, int type, String title, String desc) {
		super();
		this.id = id;
		this.type = type;
		this.title = title;
		this.desc = desc;
   }

   public TaskSon() {
	  super();
   }

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
