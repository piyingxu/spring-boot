package com.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.druid.util.StringUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtilHS256 {
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
    	Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();
       */
        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", "Service") // payload
                .withClaim("aud", "APP")
                .withClaim("user_id", user_id.toString())
                //.withIssuedAt(iatDate) // sign time
                //.withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature

        return token;
    }

    /**
     * 解密Token
     * 
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            // e.printStackTrace();
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
        Map<String, Claim> claims = verifyToken(token);
        Claim user_id_claim = claims.get("user_id");
        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
        }
        return Long.valueOf(user_id_claim.asString());
    }
    
    public static void main(String[] args) throws Exception {
    	String token = JwtUtilHS256.createToken(123l);
    	System.out.println(token);
    	token = JwtUtilHS256.createToken(123l);
    	System.out.println(token);
    	/*Map<String, Claim> tempMap = JwtUtil.verifyToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJ1c2VyX2lkIjoiMTIzIiwiaXNzIjoiU2VydmljZSJ9.1xNZBUgPLntB2OHX_BRmT5mKlekCm_lAQpCOzAqSCNs");
    	Claim user_id_claim = tempMap.get("user_id");
    	System.out.println(JSONObject.toJSONString(tempMap));
    	System.out.println(user_id_claim.asString());
    	Long uid = JwtUtil.getAppUID(token);
    	System.out.println(uid);*/
	}

}