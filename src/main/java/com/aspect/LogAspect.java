package com.aspect;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

@Component
@Aspect
public class LogAspect {

	private Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Pointcut("@annotation(Log)")
	// 定义增强范围，这个地方是指使用了注解的方法会触发
	public void bindMethod() {
		// 这个方法没有实际实现，主要用于@Before、@Around、@After 来绑定
	}

	/*
	 * @Before("bindMethod()") public void before() {
	 * logger.info("方法执行之前我先执行-----------"); }
	 */

	@Around("bindMethod()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		logger.info("开始执行-----------");
		Signature signature = point.getSignature();
		Object[] args = point.getArgs();
		logger.info("signature={},args={}", JSONObject.toJSONString(signature),
				JSONObject.toJSONString(args));
		String className = point.getTarget().getClass().getSimpleName();
		String methodName = signature.getName();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		Log log = method.getAnnotation(Log.class);
		logger.info("className={},methodName={},log={},log-before={}",
				className, methodName, log, log.before());
		Object obj = point.proceed();
		logger.info("执行结束-----------");
		return obj;
	}

	/*
	 * @After("bindMethod()") public void after() {
	 * logger.info("方法执行之后我再执行-----------"); }
	 */

	/*
	 * @Around("exeAdvice()") public Object aroundAdvice(ProceedingJoinPoint
	 * point) throws Throwable { Signature signature = point.getSignature();
	 * Object[] args = point.getArgs(); long time1 = System.currentTimeMillis();
	 * String className = point.getTarget().getClass().getSimpleName(); String
	 * methodName = signature.getName(); MethodSignature methodSignature =
	 * (MethodSignature) signature; Method method = methodSignature.getMethod();
	 * ApiOperation api = method.getAnnotation(ApiOperation.class); Log log =
	 * method.getAnnotation(Log.class); List<Object> params = new ArrayList<>();
	 * for (Object arg : args) { if (arg instanceof Serializable){
	 * params.add(arg); } } String apidesc = ""; String paramsStr = ""; try {
	 * paramsStr = GsonUtils.toJson(params); } catch (Exception e) {
	 * logger.warn("<=====请求参数json转化异常"); } if (api != null) { apidesc =
	 * api.value(); logger.info("=====>类名：[{}]，方法：[{}],描述：[{}]，请求参数req:{}",
	 * className, methodName, apidesc,paramsStr ); Object obj = point.proceed();
	 * long time2 = System.currentTimeMillis();
	 * logger.info("=====>类名：[{}]，方法：[{}],描述：[{}]，方法执行时长：{}ms，返回结果rsp:{}",
	 * className, methodName, apidesc, time2 - time1, JsonUtil.fromObject(obj));
	 * return obj; } else if (log != null){ apidesc = log.value(); if
	 * (log.before() && !log.duration()) {
	 * logger.info("=====>类名：[{}]，方法：[{}],描述：[{}]，请求参数req:{}", className,
	 * methodName, apidesc, paramsStr); } Object obj = point.proceed(); long
	 * time2 = System.currentTimeMillis(); if (log.duration()) {
	 * logger.info("=====>类名：[{}]，方法：[{}],描述：[{}]，方法执行时长：{}ms", className,
	 * methodName, apidesc, time2 - time1); } else { if (log.after()) {
	 * logger.info("=====>类名：[{}]，方法：[{}],描述：[{}]，方法执行时长：{}ms，返回结果rsp:{}",
	 * className, methodName, apidesc, time2 - time1, JsonUtil.fromObject(obj));
	 * } } return obj; }else { throw new RuntimeException("print exception"); }
	 * 
	 * }
	 * 
	 * @AfterThrowing(value = "exeAdvice()", throwing = "e") public void
	 * afterThrowingAdvice(JoinPoint joinPoint, Exception e) { String className
	 * = joinPoint.getTarget().getClass().getSimpleName(); String methodName =
	 * joinPoint.getSignature().getName(); }
	 */
}
