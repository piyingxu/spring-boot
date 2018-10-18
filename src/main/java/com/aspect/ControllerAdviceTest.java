package com.aspect;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dto.RespResult;

@ControllerAdvice
public class ControllerAdviceTest extends ResponseEntityExceptionHandler{  //ResponseEntityExceptionHandler (方法三)
	
	private Logger logger = LoggerFactory.getLogger(ControllerAdviceTest.class);
    
	// @ResponseStatus(HttpStatus.BAD_REQUEST) //跳转到404页面 (方法一)
	@ResponseBody //加上这个表示响应返回,如果不加也没会出现错误页面 (方法二)
	@ExceptionHandler(Exception.class) //可以写自定义的异常类---
    public Object doException(ArithmeticException exception) throws Throwable { //还可以声明接收其他任意参数
		String error = "出现异常，请及时处理";
        logger.error("System Exception,msg={}", error);
		return error;
    }
	
	/**
      * 根据各种异常构建 ResponseEntity 实体. 服务于以上各种异常
      * @param ex
      * @param request
      * @param specificException
      * @return
      */
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 @ExceptionHandler(NullPointerException.class)
     private ResponseEntity<Object> getResponseEntity(RuntimeException ex) {
		 String error = "空指针异常，请及时处理";
	     logger.error("System Exception,msg={}", error);
	     
         Map<String, String> responseBody = new HashMap<String, String>();
         responseBody.put("code", "9999");
         responseBody.put("msg", "服务器繁忙");
         RespResult res = new RespResult("9999", "服务器繁忙");
	     res.setData(responseBody);
         return handleExceptionInternal(ex, res, null, HttpStatus.OK, null);
     }
}
