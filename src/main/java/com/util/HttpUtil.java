package com.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

public class HttpUtil {
	
	private static Logger log = Logger.getLogger(HttpUtil.class);
	
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            /*
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            */
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sendGet error", e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    
    /**
     * 向指定URL发送GET方法的请求
     * @param url 发送请求的URL
     * @param param httprequest请求参数。
     * @param headers  需要添加的httpheader参数
     * @param timeout 请求超时时间
     * @return result 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, Map<String, String> headers) {
        String result = "";
        BufferedReader in = null;
        String reqUrl = url + "?" + param;
        int timeout = 5000;
        try {
            //构造httprequest设置
            RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).build();
            HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            HttpGet htGet = new HttpGet(reqUrl);
            // 添加http headers
            if (headers != null && headers.size() > 0) {
                for (String key : headers.keySet()) {
                    htGet.addHeader(key, headers.get(key));
                }
            }
            // 读取数据
            HttpResponse r = client.execute(htGet);
            in = new BufferedReader(new InputStreamReader(r.getEntity().getContent(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sendGet error", e);
        } finally {
            try {
                if (in != null) {
                    in = null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sendPost error", e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }  
    
    /**
	 * 获取请求参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getParametersHashMap(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		@SuppressWarnings("rawtypes")
		Enumeration paramNames = request.getParameterNames();
		while ((paramNames != null) && (paramNames.hasMoreElements())) {
			String paramName = (String) paramNames.nextElement();
			params.put(paramName, request.getParameter(paramName));
		}
		return params;
	}
	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws java.security.cert.CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws java.security.cert.CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		 
	}
	
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
	  public boolean verify(String hostname, SSLSession session) {
	     return true;
	  }
	}
	 /**
	  * post方式请求服务器(https协议)
	  * 
	  * @param url
	  *            请求地址
	  * @param content
	  *            参数
	  * @param charset
	  *            编码
	  * @return
	  * @throws NoSuchAlgorithmException
	  * @throws KeyManagementException
	  * @throws IOException
	  * @throws NoSuchProviderException
	  */
	 public static byte[] post(String url, String content, String charset)
	   throws NoSuchAlgorithmException, KeyManagementException,
	   IOException, NoSuchProviderException {
	  try {
	   TrustManager[] tm = { new TrustAnyTrustManager() };
	   // SSLContext sc = SSLContext.getInstance("SSL");
	   SSLContext sc = SSLContext.getInstance("SSL", "SunJSSE");
	   sc.init(null, tm,new java.security.SecureRandom());
	   URL console = new URL(url);
	   
	   HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
	   conn.setSSLSocketFactory(sc.getSocketFactory());
	   conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
	   conn.setDoOutput(true); 
	   conn.setDoInput(true);  
	   conn.setRequestMethod("POST");
	   conn.connect();
	   DataOutputStream out = new DataOutputStream(conn.getOutputStream());
	   out.write(content.getBytes(charset));
	   // 刷新、关闭
	   out.flush();
	   out.close();
	   InputStream is = conn.getInputStream();
	   if (is != null) {
	    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	    byte[] buffer = new byte[1024];
	    int len = 0;
	    while ((len = is.read(buffer)) != -1) {
	     outStream.write(buffer, 0, len);
	    }
	    is.close();
	    return outStream.toByteArray();
	   }
	  } catch (KeyManagementException e) {
	   e.printStackTrace();
	  }catch (NoSuchAlgorithmException e) {
	   e.printStackTrace();
	  }catch (NoSuchProviderException e) {
	   e.printStackTrace();
	  }catch (IOException e) {
	   e.printStackTrace();
	  }
	  
	  
	  
	  return null;
	 }
	 
	 
	 
	 /** 
	    * 接口调用  POST 
	    */  
	   public static String httpURLConnectionPOST (String myUrl, String param, String authorization, String URL) {  
	       String ret = "";
		   try {  
	        //传递参数  
	          
	           URL url = new URL(myUrl);  
	           // 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)  
	           // 此时cnnection只是为一个连接对象,待连接中  
	           HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
	           // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)  
	           connection.setDoOutput(true);  
	           // 设置连接输入流为true  
	           connection.setDoInput(true);  
	           // 设置请求方式为post  
	           connection.setRequestMethod("POST");  
	           // post请求缓存设为false  
	           connection.setUseCaches(false);  
	           // 设置该HttpURLConnection实例是否自动执行重定向  
	           connection.setInstanceFollowRedirects(true);  
	           // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)  
	           
	           //addRequestProperty添加相同的key不会覆盖，如果相同，内容会以{name1,name2}  
	           connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");      
	           connection.addRequestProperty("Authorization", "Bearer " + authorization);  
	           /*connection.addRequestProperty("iss", "Selcom Transsnet API");  
	           connection.addRequestProperty("timestamp", "2018-07-30 11:35:10");  
	           connection.addRequestProperty("requestParams", param);  
	           connection.addRequestProperty("URL", URL); */
	           
	           // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)  
	           connection.connect();  
	           // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)  
	           DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
	           // 格式 parm = aaa=111&bbb=222&ccc=333&ddd=444  
	           System.out.println("传递参数："+param);  
	           // 将参数输出到连接  
	           dataout.writeBytes(param);  
	           // 输出完成后刷新并关闭流  
	           dataout.flush();  
	           dataout.close(); // 重要且易忽略步骤 (关闭流,切记!)   
	           //System.out.println(connection.getResponseCode());  
	           // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)  
	           BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));   
	           String line;  
	           StringBuilder sb = new StringBuilder(); // 用来存储响应数据  
	             
	           // 循环读取流,若不到结尾处  
	           while ((line = bf.readLine()) != null) {  
	            //sb.append(bf.readLine());  
	            sb.append(line).append(System.getProperty("line.separator"));  
	           }  
	           bf.close();    // 重要且易忽略步骤 (关闭流,切记!)   
	           connection.disconnect(); // 销毁连接  
	           System.out.println(sb.toString());  
	           ret = sb.toString();
	       } catch (Exception e) {  
	           e.printStackTrace();  
	       }  
		   return ret;
	   }  
    public static void main (String str[]) throws Exception {
    	/*String requestStr = sendPost("http://127.0.0.1:3333/api/v1/user/login/account", 
    			"account=123456&password=123456&sign=123&timestamp=1111");
    	System.out.println(requestStr);*/
    	
    	/*String requestStr = sendPost("http://192.168.3.145:8888/ddzlobby/store/getMenu", 
    			"uid=123456&token=123456&storeId=8001&menuType=2");
    	System.out.println(requestStr);
    	*/
    	/*
    	String requestStr = sendPost("http://127.0.0.1:3333/ddzlobby/store/getChargeType", 
    			"uid=123456&token=123456&menuId=1");
    	System.out.println(requestStr);
    	*/
    	/*String requestStr = sendPost("http://127.0.0.1:8888/ddzlobby/deposite/order", 
    			"uid=123456&token=123456&chargeMenuId=2&channelId=2");
    	System.out.println(requestStr);*/
    	/*int uid = 50202;
    	int diamond = 0;
    	int coin = 6000;
    	String pushUrl = "http://39.108.109.85:5401/api/v1/award/push";
    	HttpRequest.sendGet(pushUrl, "uid=" + uid + "&diamond=" + diamond + "&coin=" + coin);
    	*/
    	 /*int uid= 50202;
    	 Map <String,Long> magicMap = new HashMap<String,Long>();
    	 magicMap.put(RedisConst.USER_COIN, 1000l);
    	 String awardJson = JsonUtil.Object2JSON(magicMap);
    	 awardJson = URLEncoder.encode(awardJson, "utf-8"); */
		 /*String retString = HttpRequest.sendPost("http://127.0.0.1:8888/api/v1/exchange/store",
         "token=ZGI5MmYwNDktZjI1MC00NGM5LWE3NDQtYmRlMWJlYmE0OTRhaTVpNTAyMDI=&sign=1&timestamp=1&storeId=8001&type=2");
		 System.out.println(retString);
		 */
    	
    	
    	/*
		 String retString = HttpRequest.sendPost("http://127.0.0.1:8888/api/v1/exchange/store/address",
		         "token=ZGI5MmYwNDktZjI1MC00NGM5LWE3NDQtYmRlMWJlYmE0OTRhaTVpNTAyMDI=&sign=1&timestamp=1&qq=5166&email=qq.com&consignee=w小瓶&mobile=18825200924&address=湖北");
				 System.out.println(retString);
		 */
    	

//http://127.0.0.1:8888/api/v1/exchange/store/exchange?token=ZGI5MmYwNDktZjI1MC00NGM5LWE3NDQtYmRlMWJlYmE0OTRhaTVpNTAyMDI=&sign=1&timestamp=1&menuId=17&mobile=111&name=222&address=333
    	    /*for (int i=0; i<10000; i++) {
    		 Thread thread = new Thread(){
    			   public void run(){
    				   String retString = HttpRequest.sendPost("http://127.0.0.1:8888/api/v1/mail",
    		    		         "token=NTNhNWY2MzItOTlmMC00Njc0LWJmODAtM2Y2YmJmZDM4OTU0aTVpNTAzMzU=&sign=1&timestamp=1");
    		    				 //System.out.println(retString);
    			   }
    			};
    		 thread.start();*/
    	/* String retString = HttpRequest.sendPost("http://127.0.0.1:8888/api/ddz/common/getvenue",
		         "token=MzAyZGRjYmUtZjNlMS00OTE5LWIwNGItYTgzMGZkMmFlYjI0aTVpNTA0NzE=");
				 System.out.println(retString);
    		 */
        
    /*	String retString = HttpRequest.sendPost("http://127.0.0.1:8888/api/v1/marketing/regwelfare",
		         "token=YzU0NDg3MTItNDljMy00NWNkLTkwYmMtNzdiMzdjNTE3MWQ1aTVpNTA0OTU=");
				 System.out.println(retString);
   */
    	/*for (int i=0;i<100;i++) {
		    Thread t = new Thread(new Runnable(){
			    public void run(){
			    	String ret= sendPost("http://127.0.0.1:8888/api/ddz/common/transfer.do", "token=MDc2ZjhlZTQtYTg2ZS00Nzc4LTljNDMtMzViMWU0NzY4YzA5aTVpNTA4ODI=&sign=1&timestamp=1&targetUid=50202&transferMoney=200");
			        System.out.println(ret);
			   }
			});
			t.start();
    	}*/
    	/*
    	String ret= sendPost("http://127.0.0.1:8888/api/ddzlobby/store/showTreaNum", "token=MDc2ZjhlZTQtYTg2ZS00Nzc4LTljNDMtMzViMWU0NzY4YzA5aTVpNTA4ODI=&storeId=7000&status=3&sign=1&timestamp=1&menuId=1353&type=1&times=1&periods=3");
        System.out.println(ret);
        
        */
    	/*
        String ret= sendPost("http://127.0.0.1:8888/api/v1/user/login.account", "channelId=ddz0001&version=1.2&sign=1&timestamp=1&menuId=1353&type=2&account=50882&password=888888999&telPhone=18825200924");
        System.out.println(ret);*/
    	/*
    	   String ret= sendPost("http://127.0.0.1:8888/api/ddz/common/testpp", "token=MDc2ZjhlZTQtYTg2ZS00Nzc4LTljNDMtMzViMWU0NzY4YzA5aTVpNTA4ODI=&storeId=7000&status=3&sign=1&timestamp=1&menuId=1353&type=1&verifyCode=364464&password=888888999&telPhone=18825200924");
           System.out.println(ret);
          */ 
/*    	String ret = sendGet("http://test-fishportal.5i-games.com:8080/admin.php/Public/login.html", "username=test&password=888888");
    	System.out.println(ret);
    	       ret = sendGet("http://test-fishportal.5i-games.com:8080/admin.php/LuckyBag/saveLuckyBag.html", "");
		System.out.println(ret);*/
		
		/*for (int i=0;i<100;i++) {
			sendGet("http://localhost:8601/testAsyn", "");
		}*/
		
		
		
		String ret = sendPost("http://10.200.110.19:9300/app_log/log/_search", "pretty -H \"Content-Type: application/json\" -d");
		System.out.println(ret);
    	
    }
}