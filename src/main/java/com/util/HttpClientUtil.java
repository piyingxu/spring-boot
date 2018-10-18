package com.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.support.json.JSONUtils;


/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2018年8月21日 下午2:42:41
 */
public class HttpClientUtil {

	private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @param header
	 * @param params
	 * @return
	 */
	public static String doPost(String url, Map<String, String> header, String params) {
		log.info("post request,url={},header={},params={}", url, header, params);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		header.forEach((k, v) -> httpPost.setHeader(k, v));
		String charSet = "UTF-8";
		if (params != null) {
			StringEntity entity = new StringEntity(params, charSet);
			httpPost.setEntity(entity);
		}
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			HttpEntity responseEntity = null;
			String jsonString = null;
			if (state == HttpStatus.SC_OK || state == HttpStatus.SC_CREATED) {
				responseEntity = response.getEntity();
				jsonString = EntityUtils.toString(responseEntity);
				//log.info("post success responseEntity={}", jsonString);
				return jsonString;
			} else {
				responseEntity = response.getEntity();
				jsonString = EntityUtils.toString(responseEntity);
				log.error("post fail,state={},responseEntity={}", state, jsonString);
				return jsonString;
			}
		} catch (Exception e) {
			log.error("post url={} exception=", url, e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main (String str[]) throws Exception {
		Map<String, String> header = new HashMap<>();
		header.put("Accept", "application/json;charset=utf-8");
		header.put("Content-Type", "application/json;charset=utf-8");
		AuditLogPageReq req = new AuditLogPageReq();
		req.setUserId("1111");
		for (int i=0;i<100;i++) {
		//while(true) {
			Thread.sleep(500);
			Thread t = new Thread(new Runnable(){  
	            public void run() {  
	            	HttpClientUtil.doPost("http://10.200.110.25:8080/api/mfront/auditLog/2900/quary", header, JSONUtils.toJSONString(req));
	            	System.out.println("ok");
	            }
	        });  
	        t.start(); 
		}
	 }


}
