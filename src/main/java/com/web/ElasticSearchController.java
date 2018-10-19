package com.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dto.AutoLog;
import com.service.ElasticSearchService;

@Api(description = "ES相关", tags = "Elastic")
@RestController
@RequestMapping("/es")
public class ElasticSearchController {
	
    @Autowired
    private ElasticSearchService empServive;

    @ApiOperation("1、测试")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test (){
    	return "ok";
    }
	
	//增加
    @ApiOperation("2、es新增")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(@RequestParam("className") String className, @RequestParam("time") String time) {
		AutoLog log = new AutoLog();
		log.setApplicationName("m-front");
		log.setIp("127.0.0.1");
		log.setInstanceName("front");
		log.setLoglevel("info");
		log.setClassName(className);
		log.setParentId("0");
		log.setSpanId("0");
		log.setStack_trace("trace");
		log.setTimestamps(time);
		log.setThread("main");
		log.setMessage("hellow word");
		Object res = empServive.addAutoLog(log);
		return JSONObject.toJSONString(res);
	}
	
	 //查询
     @ApiOperation("3、数据库查询")
     @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public String queryAll() {
		return JSONObject.toJSONString(empServive.findAllAutoLog());
	}
	
	//分页查询
    @ApiOperation("4、数据库分页查询")
    @RequestMapping(value = "/queryAllByPage", method = RequestMethod.GET)
	public String queryAllByPage(@RequestParam("startPage") int startPage, @RequestParam("pageSize") int pageSize) {
		return JSONObject.toJSONString(empServive.findByPage(startPage, pageSize));
	}
		
	
	
}