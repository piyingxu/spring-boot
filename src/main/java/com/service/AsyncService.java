package com.service;

import java.util.concurrent.Future;
import com.dto.PageDto;
import com.model.User;

public interface AsyncService {
	
	public void asyncInvokeSimplest(int afterSecond);
	
	public String asyncInvokeWithException(String s);
	 
	public Future<String> asyncInvokeReturnFuture(int i);
	
	public User selectByPrimaryKey(Integer userId);
	
	public PageDto<User> selectAll(int startPage, int pageSize);
	
	public int insert(User record);
	
	public void simpleMethod();
	       
}
