package com.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.annotation.TestAnnoOne;
import com.annotation.TestAnnoThr;
import com.annotation.TestAnnoTwo;
import com.aspect.Log;
import com.dto.PageDto;
import com.dto.SimpleObj;
import com.feign.FeignClientTest;
import com.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.service.AsyncService;

@RestController
public class WebController {

	private Logger logger = LoggerFactory.getLogger(WebController.class);

	@Autowired
	private FeignClientTest feignClientTest;

	@Resource
	private AsyncService asyncService;
	
	@Resource
	private TestAnnoOne testAnnoOne;
	
	@Resource(name = "myBeanTwo") //来源于@Bean注解
	private TestAnnoTwo testAnnoTwo;
	
	@Resource(name = "TestAnnoTwo")
	private TestAnnoTwo testAnnoTwo_1; //来源于@Component("TestAnnoTwo")
	
	@Autowired
	private TestAnnoThr testAnnoThr;
	
	@Resource(name = "piyingxu")
	private TestAnnoThr testAnnoThr_1; //来源于@Configuration("piyingxu")
	
	@Autowired
	private SimpleObj simpleObj;
	/*
	@Autowired
	private OtherConfig otherConfig;*/
	
	@RequestMapping("/api/testinfo")
	@HystrixCommand(groupKey = "myGroup", // group标识，一个group使用一个线程池
	commandKey = "doInfo", // 当前执行的方法
	fallbackMethod = "serviceFailure", commandProperties = {
			@HystrixProperty(name = "execution.timeout.enabled", value = "true"),// 是否打开超时
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50000"),// 指定多久超时，单位毫秒。超时进fallback
			@HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 是否开启熔断
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 判断熔断的最少请求数，默认是10；只有在一个统计窗口内处理的请求数量达到这个阈值，才会进行熔断与否的判断
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10") // 判断熔断的阈值，默认值50，表示在一个统计窗口内有50%的请求处理失败，会触发熔断
	}, threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "30"),
			@HystrixProperty(name = "maxQueueSize", value = "101"),
			@HystrixProperty(name = "keepAliveTimeMinutes", value = "4"),
			@HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
			@HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440") })
	@Log(before = false, after = false, duration = true)
	public Object doInfo(@RequestParam("userName") String userName)
			throws Exception {
		logger.info("开始去请求服务器提供者");
		/*
		 * long systime = System.currentTimeMillis(); if (systime % 2 == 0) {
		 * throw new ArithmeticException("test error"); } else { throw new
		 * NullPointerException("test error"); }
		 */
		String object = feignClientTest.info(userName);
		return object;
	}

	public String serviceFailure(String userName) {
		return userName + ",the service is not available !";
	}

	@RequestMapping(value = "/health", method = RequestMethod.GET)
	public String health() {
		return "hello health ";
	}

	@RequestMapping(value = "/testAsyn", method = RequestMethod.GET)
	public String testAsyn() {
		asyncService.asyncInvokeSimplest(1000 * 1000);
		// String ret = asyncService.asyncInvokeWithException("xp,");
		// asyncService.asyncInvokeReturnFuture(99);
		logger.info("doOther");
		return "ok";
	}

	@Log
	@RequestMapping(value = "/testQuery", method = RequestMethod.GET)
	public String testQuery() {
		User user = asyncService.selectByPrimaryKey(1);
		return JSONObject.toJSONString(user);
	}

	@RequestMapping(value = "/testQueryAll", method = RequestMethod.GET)
	public String testQueryAll(@RequestParam int startPage,
			@RequestParam int pageSize) {
		PageDto<User> list = asyncService.selectAll(startPage, pageSize);
		return JSONObject.toJSONString(list);
	}

	@Log
	@RequestMapping(value = "/testAdd", method = RequestMethod.GET)
	public Object testAdd() {
		int ret = 0;
		try {
			User user = new User();
			user.setName("xxx");
			user.setAge(12);
			user.setPwd("111111");
			ret = asyncService.insert(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("testAdd error", e);
		}
		return ret;
	}
	
	@Log
	@RequestMapping(value = "/testAnno", method = RequestMethod.GET)
	public Object testAnno() {
		int ret = 0;
		//testAnnoOne.test();
		//testAnnoTwo.getName();
		//testAnnoThr.test();
		//simpleObj.getName();
		//testAnnoTwo_1.getName();
		//testAnnoTwo_1.maxTotal();
		//testAnnoThr_1.test();
		//System.out.println(JSONUtils.toJSONString(otherConfig.getConsumers().get(0).get("id")));
		//System.out.println(JSONUtils.toJSONString(otherConfig.getConsumers().get(1).get("id")));
		return ret;
	}
	
	

}
