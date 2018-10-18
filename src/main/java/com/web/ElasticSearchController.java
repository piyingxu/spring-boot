package com.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dto.AutoLog;
import com.service.ElasticSearchService;
 
@RestController
@RequestMapping("/es")
public class ElasticSearchController {
	
    @Autowired
    private ElasticSearchService empServive;
    
    @RequestMapping("/test")
	public String test (){
    	return "ok";
    }
	
	//增加
	@RequestMapping("/add")
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
	@RequestMapping("/queryAll")
	public String queryAll() {
		return JSONObject.toJSONString(empServive.findAllAutoLog());
	}
	
	//分页查询
	@RequestMapping("/queryAllByPage")
	public String queryAllByPage(@RequestParam("startPage") int startPage, @RequestParam("pageSize") int pageSize) {
		return JSONObject.toJSONString(empServive.findByPage(startPage, pageSize));
	}
		
	
	
}