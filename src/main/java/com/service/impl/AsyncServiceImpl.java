package com.service.impl;

import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aspect.Log;
import com.dto.PageDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.UserMapper;
import com.model.User;
import com.service.AsyncService;

@Service
public class AsyncServiceImpl implements AsyncService {
	
	@Autowired
	private UserMapper userMapper;

	private static final Logger log = LoggerFactory.getLogger(AsyncServiceImpl.class);

	/**
	 * 最简单的异步调用，返回值为void
	 * afterSecond 延迟执行
	 */
	@Log
	@Async
	public void asyncInvokeSimplest(int afterSecond) {
		try {
			System.out.println("任务正在执行=======");
			Thread.sleep(afterSecond); 
			log.info("start do sth");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 带参数的异步调用 异步方法可以传入参数 对于返回值是void，异常会被AsyncUncaughtExceptionHandler处理掉
	 * 
	 * @param s -----不能有其他的返回
	 */
	@Async
	public String asyncInvokeWithException(String s) {
		log.info("asyncInvokeWithParameter, parementer={}", s);
		try {
			// Thread.sleep(100);
			s += "ok";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 异常调用返回Future
	 * 对于返回值是Future，不会被AsyncUncaughtExceptionHandler处理，需要我们在方法中捕获异常并处理
	 * 或者在调用方在调用Futrue.get时捕获异常进行处理
	 * 
	 * @param i
	 * @return
	 */
	@Async
	public Future<String> asyncInvokeReturnFuture(int i) {
		log.info("asyncInvokeReturnFuture, parementer={}", i);
		Future<String> future;
		try {
			Thread.sleep(1000 * 1);
			future = new AsyncResult<>("success:" + i);
			// throw new IllegalArgumentException("a");
		} catch (InterruptedException e) {
			future = new AsyncResult<>("error");
		} catch (IllegalArgumentException e) {
			future = new AsyncResult<>("error-IllegalArgumentException");
		}
		return future;
	}

	@Override
	public User selectByPrimaryKey(Integer userId) {
		return userMapper.selectByPrimaryKey(userId);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageDto<User> selectAll(int startPage, int pageSize) {
		PageHelper.startPage(startPage, pageSize);
		List<User> list = userMapper.selectAllUser();
		//用PageInfo对结果进行包装
		PageInfo page = new PageInfo(list);
		PageDto<User> pageDto = new PageDto<User>();
		pageDto.setCurPage(page.getPages());
		pageDto.setPageSize(page.getPageSize());
		pageDto.setList(list);
		pageDto.setTotalPage(page.getPageNum());
		pageDto.setTotal(page.getEndRow());
		return pageDto;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insert(User record) {
		//int a =1/0;测试回滚
		return userMapper.insert(record);
	}
	
	public void simpleMethod() {
		System.out.println("simpleMethod is call");
	}

}
