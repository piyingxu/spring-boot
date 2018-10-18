package com.util;

import java.net.URLEncoder;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtUtilOld {
	
	private static final  String MAC_INSTANCE_NAME = "HMacSHA256";

	public static String Hmacsha256(String secret, String message) throws Exception {
	    Mac hmac_sha256 = Mac.getInstance(MAC_INSTANCE_NAME);
	    SecretKeySpec key = new SecretKeySpec(secret.getBytes(), MAC_INSTANCE_NAME);
	    hmac_sha256.init(key);
	    byte[] buff = hmac_sha256.doFinal(message.getBytes());
	    //Base64.encodeBase64URLSafeString(buff);
	    return base64UrlEncode(buff);
	}
	
	public static String base64UrlEncode(byte[] simple) {	
		String s = new String(Base64.getEncoder().encode(simple)); // Regular base64 encoder
		s = s.split("=")[0]; // Remove any trailing '='s
		s = s.replace('+', '-'); // 62nd char of encoding
		s = s.replace('/', '_'); // 63rd char of encoding
	    return s;
    }

    public static byte[] base64UrlDecode(String cipher) {
		String s = cipher;
		s = s.replace('-', '+'); // 62nd char of encoding
		s = s.replace('_', '/'); // 63rd char of encoding
		switch (s.length() % 4) { // Pad with trailing '='s
			case 0:
				break; // No pad chars in this case
			case 2:
				s += "==";
				break; // Two pad chars
			case 3:
				s += "=";
				break; // One pad char
			default:
				System.err.println("Illegal base64url String!");
		}
		return Base64.getDecoder().decode(s); // Standard base64 decoder
    }

	public static String testJWT(String payload) throws Exception {
		
	    String prikey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDcDxNsQ+e1JQAvDolwpCakv6XO5BOtmmpflbhTK09AKz6rZAgSmfBZXQwU4qg6oz8k5pP8KyO8D+WkFvtj9MDumGfzhcfxGGja8GNKFvrjvAR4oqiZZjD2l+pgfTjypo4kcaLVHaCAVkXfGP11iJ5UExjhXhxk5j+OtCumId+FTaUpjHNhiN7V3ZP38cggIzE21hbfmiKg+EsZZG8X6f98yoGuFPwLcy9H76MPrA9IKBr54+l9PJmFv13iBD82sibnC0Fi1VY0nzZgqBb2T6jR8UJbPx0SlZZeH8mcbXcq1WlZEsjt8PjjzsCV83SCn1ClfdhH7JTPxP9qLlX41Z3dAgMBAAECggEBAKWWxpPKSToa3JeMRcm4C8ge2kLjhxc0QeUpQWl8BoePwvmvLQ/qPFzSUnwIznRJUQOQajzvrVUfVTrzfDmL+/3OOzKZMYnvRz+wcdZXknT5jxfDtkCwEBoO2HPA5rBYuk8cH15ki9jmkm89W8QsiI9VS3ySl6UpSRw14T6C8LJaBFIdDIXtO/daslQGvwm0W+laWnHPMbr1kw6Pm5yeiuqBvZzvUD+MVwzEvYZDW3Y7Z+cbmhA4K1mBxisQqPk5aIHWpRwRYBO51MOW9wI4g9rSY1DAmFNBqPWrv+Okom0EL/mbUJSj8zghtKu2jknLOQhqwAb/cQTL9DYJUzlC7vUCgYEA+U8FaFwmm6DVvlP+6M4AUkXQMYq+7H/ntOpRNMLYQbXAZOzIhESMQ22umPYmdE6Ost3FAWGMQTH+xNLvM4/zVMDLBUv9Je8hGP5P3FCuPVWgTrtDTl4pyzWP/OQoFI0kEIPLCBMIqT1SVHtZFfboctIA5nQ/2qBkhpYdj6nouY8CgYEA4fcU+a15N9yLZ7L8rD+PcfHww4qorvjvlNlccUdzwy5i1UZqOPBu4PdZ8zpEoSuN1xU5bAlCWPOnWZfX1jrrL4KplA82BcAfdmuHdcCJ4LRaY242LmC63ROTVk2ckddmNKLzbYcKzTkat1DcAXzqv1iC+B6rGjWJ8Zw00LKoA9MCgYBY+U+nwANzVUITC+0UbriEpC+6Dpc21ir+UQWsO8FsKg5AtpSVR9V7FwDRzD9LEOIyiZ0Och7ZAZu36MO/didUXv3VpRnyWNZKEjC0IwMcFpwAnSpXJJnr0PDTQyXet8RgNHjYJ9rWc3EXf3H6ucSZfcMee+xx/P4DXj2skOnilwKBgCobFHmB7biPDBsCqzqdjWk6IBcP22bfVCV0a1lgax3PovMC8AA0LTUWYliEXw15RDDz74NGJmrU3DZBqSJuUCzGODsyqmpf5nz2hINYoViRwtYMT2RM+gUABNBsHeS/MnhUdO/P3h9nqKbIFnbghA2rvJvnexKjL1UqOl05LT2pAoGBAJy7OqFonELLtinE4s6ouVlJjjlt3TKByZSVzJwsS00q09QBQ8NLtDQHr/rOotaEXUxpt+/6fwwD7dzLvEqo9VPf7jGYzaQGOkP6shCBWrAr+jbsd0XhST9F1ikWtpNcWlhs+JviKRS71Mhn1lKb4xErBOfI2KYjfLFCTVgD5dAV";
	    String header = "{\"typ\":\"JWT\",\"alg\":\"HS256\"}";

	    String base64Header = base64UrlEncode(header.getBytes());
	    String base64Payload = base64UrlEncode(payload.getBytes());
	    String signature = JwtUtilOld.Hmacsha256(prikey, base64Header + "." + base64Payload);

	    String token = base64Header + "." + base64Payload  + "." + signature;
	    System.out.println(token);
	    return token;
	}
	
	public static void main(String[] args) throws Exception {
		
		/*
		String payload = JSONObject.toJSONString(body);
		String token = JwtUtilOld.testJWT(payload);
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("Authorization", token);
		tempMap.put("iss", "xxxx");
		tempMap.put("method", "open account");
		tempMap.put("timestamp", "2018-08-27");
		tempMap.put("requestParams", payload);
		tempMap.put("URL", url);
		String sendParam = JSONObject.toJSONString(tempMap);
		*/
		
		RSAPublicKey publicKey = (RSAPublicKey) RSAUtils.getPublicKeyByStr(RSAUtils.SEL_PUBLIC_KEY);
		RSAPrivateKey privateKey = (RSAPrivateKey) RSAUtils.getPriKeyByStr(RSAUtils.SEL_PRIVATE_KEY);
		
		String transid = String.valueOf(System.currentTimeMillis());
		String msisdn = "18825200924";
		String firstName = "pi";
		String lastName = "yingxu";
		String addressCountry = "shenzhen";
		String currency = "USD";
		

		ReqBody reqBody = new ReqBody();
		reqBody.setTransid(transid);
		reqBody.setMsisdn(msisdn);
		reqBody.setFirstName(firstName);
		reqBody.setLastName(lastName);
		reqBody.setAddressCountry(addressCountry);
		reqBody.setCurrency(currency);
		
		Map<String, Object> bodymap = new HashMap<>();
		String bodyParam = JSONObject.toJSONString(reqBody);
		String token= "";
		String bodymapStr = "";
		try {
			Map<String, Object> headmap = new HashMap<>();
			headmap.put("alg", "RS256");
			headmap.put("typ", "JWT");
			headmap.put("iss", "Selcom Transsnet");
			headmap.put("sub", "Selcom@transsnet.net");
			headmap.put("aud", "https://transnnet.selcom.net");
			headmap.put("exp", "24h");
			/*
			bodymap.put("iss", "RS256");
			bodymap.put("method", "open account");
			bodymap.put("timestamp", transid);
			bodymap.put("requestParams", bodyParam);
			*/
			bodymapStr = JSONObject.toJSONString(bodymap);
		    Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
		    token = JWT.create().withHeader(headmap)
	                .withClaim("transid", transid)
	                .withClaim("msisdn", msisdn)
	                .withClaim("firstName", firstName)
	                .withClaim("lastName", lastName)
	                .withClaim("addressCountry", addressCountry)
	                .withClaim("currency", currency)
	                .sign(algorithm); 
		    JWT.create();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String url = "http://34.245.13.82:8080/transsnet";
		//sendParam = URLEncoder.encode(sendParam, "utf-8");
		System.out.println("url:" + url + "?" + bodyParam);
		
		String ret = HttpUtil.sendPost(url, bodyParam);
		//String ret = HttpUtil.httpURLConnectionPOST(url, bodyParam, token, url);
		System.out.println(ret);
	}
	
	
	
}
