package com.service;

import java.util.Map;

public interface HttpAPIService {
	
	public String doGet(String url);
	
	public String doGet(String url, Map<String, Object> map);
	
	public String doPost(String url, Map<String, Object> map);
	 
	public String doPost(String url);
	       
}
