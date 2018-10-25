package com.web;

import javax.annotation.Resource;

import com.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.aspect.Log;
import com.dto.PageDto;
import com.dto.SimpleObj;
import com.feign.FeignClientTest;
import com.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.service.AsyncService;

@Api(description = "账户相关", tags = "USER")
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

    @Resource(name = "beanAA") //来源于@Bean注解，根据name（ConditionalOnMissingBeanConfig）
    private A a;

    @Resource(type =B.class) //来源于@Bean注解，根据type（ConditionalOnMissingBeanConfig）
    private B b;
	
	@Autowired
	private SimpleObj simpleObj;


    @ApiOperation("0、测试注解调用")
    @RequestMapping(value = "/anno", method = RequestMethod.GET)
    public void testAnnoSimple() {
        testAnnoOne.test();
        a.AdoSth();
        b.BdoSth();
    }


	/*
	@Autowired
	private OtherConfig otherConfig;*/
    @ApiOperation("1、测试微服务调用")
    @RequestMapping(value = "/doInfo", method = RequestMethod.GET)
	@HystrixCommand(groupKey = "myGroup", // group标识，一个group使用一个线程池
	commandKey = "doInfo", // 当前执行的方法
	fallbackMethod = "serviceFailure", commandProperties = {
			@HystrixProperty(name = "execution.timeout.enabled", value = "false"),// 是否打开超时
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50000"),// 指定多久超时，单位毫秒。超时进fallback
			@HystrixProperty(name = "circuitBreaker.enabled", value = "false"), // 是否开启熔断
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
        logger.debug("开始去请求服务器提供者");
		logger.info("开始去请求服务器提供者");
        logger.warn("开始去请求服务器提供者");
        logger.error("开始去请求服务器提供者");
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

    @ApiOperation("2、心跳")
	@RequestMapping(value = "/health", method = RequestMethod.GET)
	public String health() {
		return "hello health ";
	}

    @ApiOperation("3、测试异步调用")
	@RequestMapping(value = "/testAsyn", method = RequestMethod.GET)
	public String testAsyn() {
		asyncService.asyncInvokeSimplest(1 * 1000);
		// String ret = asyncService.asyncInvokeWithException("xp,");
		// asyncService.asyncInvokeReturnFuture(99);
		logger.info("doOther");
		return "ok";
	}

	@Log
    @ApiOperation("4、数据库查询")
	@RequestMapping(value = "/testQuery", method = RequestMethod.GET)
	public String testQuery() {
		User user = asyncService.selectByPrimaryKey(1);
		return JSONObject.toJSONString(user);
	}

    @ApiOperation("5、数据库查询所有")
	@RequestMapping(value = "/testQueryAll", method = RequestMethod.GET)
	public String testQueryAll(@RequestParam int startPage,
			@RequestParam int pageSize) {
		PageDto<User> list = asyncService.selectAll(startPage, pageSize);
		return JSONObject.toJSONString(list);
	}

    @ApiOperation("6、数据库新增")
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

    @ApiOperation("7、数据库新增")
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
