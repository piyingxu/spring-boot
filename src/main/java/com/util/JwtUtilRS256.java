package com.util;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtilRS256 {
    /**
     * APP登录Token的生成和解析
     * 
     */
    /** token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj */
	
    public static final String SECRET = "piyingxu";
    /** token 过期时间: 10天 */
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 10;

    /**
     * JWT生成Token.<br/>
     * JWT构成: header, payload, signature
     * @param user_id 登录成功后用户user_id, 参数user_id不可传空
     */
    public static String createToken(Long user_id) throws Exception {
    	/*
    	// expire time
        Date iatDate = new Date();
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();
        */
        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "RS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        
        /*
        Map<String, Object> keyMap = RSAUtils.genKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyMap.get(RSAUtils.PUBLIC_KEY);
		RSAPrivateKey privateKey = (RSAPrivateKey) keyMap.get(RSAUtils.PRIVATE_KEY);
		*/
        RSAPublicKey publicKey = (RSAPublicKey) RSAUtils.getPublicKeyByStr(RSAUtils.TEST_PUBLIC_KEY);
        RSAPrivateKey privateKey = (RSAPrivateKey) RSAUtils.getPriKeyByStr(RSAUtils.TEST_PRIVATE_KEY);
        
		System.out.println("公钥为:" + JSONObject.toJSONString(publicKey.getEncoded()));
		System.out.println("私钥为:" + JSONObject.toJSONString(privateKey.getEncoded()));
		
		String token = "";
		try {
		    Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
		    token = JWT.create().withHeader(map) // header
	                .withClaim("iss", "Service") // payload
	                .withClaim("aud", "APP")
	                .withClaim("user_id", user_id.toString())
	                //.withIssuedAt(iatDate) // sign time
	                //.withExpiresAt(expiresDate) // expire time
	                .sign(algorithm); // signature
		    Builder creator = JWT.create();
		    
		    
		} catch (Exception e){
			e.printStackTrace();
		}
		
        return token;
    }

    /**
     * 解密Token
     * 
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token, String pubKey, String priKey) {
    	RSAPublicKey publicKey = (RSAPublicKey) RSAUtils.getPublicKeyByStr(pubKey);
        RSAPrivateKey privateKey = (RSAPrivateKey) RSAUtils.getPriKeyByStr(priKey);
        DecodedJWT jwt = null;
        try {
        	 Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        	 JWTVerifier verifier = JWT.require(algorithm).build(); //Reusable verifier instance
        	 jwt = verifier.verify(token);
        } catch (Exception e) {
            e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
        }
        return jwt.getClaims();
    }

    /**
     * 根据Token获取user_id
     * 
     * @param token
     * @return user_id
     */
    public static Long getAppUID(String token) {
        Map<String, Claim> claims = verifyToken(token, RSAUtils.TEST_PUBLIC_KEY, RSAUtils.TEST_PRIVATE_KEY);
        Claim user_id_claim = claims.get("user_id");
        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
        }
        return Long.valueOf(user_id_claim.asString());
    }
    
    public static void main(String[] args) throws Exception {
    	String token = JwtUtilRS256.createToken(123456l);
    	System.out.println(token);
    	Map<String, Claim> tempMap = JwtUtilRS256.verifyToken(token, RSAUtils.TEST_PUBLIC_KEY, RSAUtils.TEST_PRIVATE_KEY);
    	Claim user_id_claim = tempMap.get("user_id");
    	System.out.println(JSONObject.toJSONString(tempMap));
    	System.out.println(user_id_claim.asString());
    	Long uid = JwtUtilRS256.getAppUID(token);
    	System.out.println(uid);
    	
	}

}