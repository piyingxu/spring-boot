package com.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Test {

	public static void main(String[] args) throws Exception {
		/*
		Test.strForMat("pp");
		Test.testJs();
		Arrays.asList( "a", "b", "d" ).forEach(
			e -> System.out.println( e )
		);
		List<Integer> tempList = new ArrayList<Integer>();
		tempList.add(1);tempList.add(2);tempList.add(3);
		tempList.forEach(
			item ->	(
				System.out.println(item)
			)
		);
		tempList.forEach(
			item ->	{
				if (item == 2) {
					System.out.println(item);
				}
			}
		);
		
		List<String> bankCode = new ArrayList<>();
		boolean isnull = CollectionUtils.isEmpty(bankCode);
		System.out.println(isnull);
		System.out.println(StringUtils.isNotBlank(""));
		
        List<String> strlist = Arrays.asList(new String[]{"aa","bb","cc"});
        //strlist = Arrays.asList("abc");
        System.out.println(JSONObject.toJSONString(strlist));
        */
		/*
		for (int i=0;i<5;i++) {
			Map<String, Object> key = RSAUtils.genKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) key.get("RSAPublicKey");
			RSAPrivateKey privateKey = (RSAPrivateKey) key.get("RSAPrivateKey");
			System.out.println(JSONObject.toJSONString(publicKey.getEncoded()));
	        System.out.println(JSONObject.toJSONString(privateKey.getEncoded()));
		}
		*/
		/*LocalDateTime endTime = LocalDateTime.now();
		LocalDateTime startTime = endTime.plusDays(-30);
		
		
		//LocalDateTime
		System.out.println(startTime);
		System.out.println(endTime);*/
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("d-MMM-yy", Locale.ENGLISH);
		Date date = dateFormat.parse("3-JUN-92");
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));
	}
	
	public static void strForMat (String name) {
		System.out.println(String.format("我是%s", name));
	}

	public static void testJs () {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			System.out.println( engine.getClass().getName() );
			System.out.println( "Result:" + engine.eval( "function f() { return 1; }; f() + 1;" ) );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testHttpClient () {
		/*
		//HttpClient httpclient = new DefaultHttpClient();
		String smsUrl="https://www.hao123.com/";
        HttpPost httppost = new HttpPost(smsUrl);
        String strResult = "";
       */
		
		
		
	}
	 

	
	
	
	
	
	
	
	
	
}
