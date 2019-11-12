package com.util;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/11/11 17:43
 */
public class RsaDemo {

    private static final String TEST_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdUlV0xXBGTLhfKFIrB3/YTSx1M80/SzI2zDx/u+ezopxm0N07graR3+A6I9YSUuZaR5NYVLYksO3TvgH7iPBZ9Y7ZriRE56N4Xww7KA79TQvn04b5T4O7mjsrrAnEma25d/guUZVSriRYGtyPnSvF7kAKragNtMP/mZIexQuGYwIDAQAB";

    private static final String TEST_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ1SVXTFcEZMuF8oUisHf9hNLHUzzT9LMjbMPH+757OinGbQ3TuCtpHf4Doj1hJS5lpHk1hUtiSw7dO+AfuI8Fn1jtmuJETno3hfDDsoDv1NC+fThvlPg7uaOyusCcSZrbl3+C5RlVKuJFga3I+dK8XuQAqtqA20w/+Zkh7FC4ZjAgMBAAECgYB8lWX9KhmmYj0jhi/DyZWRelP5oIMqW2cxg/1o/ioX1G9c8IwyA2qHDK5p/FUbf7DRz5q8uaDgX4iHRRIW2rPSk65nZRW1lCp3rMCGxDmL5gM3l7jwuh8jg5qvchgEXbcn79wGL1NBx8LF2fq3jd2UuE9OD6ZQS5eOHWNsA5FxuQJBAPt1CDCqScoaMDsorr5Srb35DVLNSk/u5ra/vGRcYboPz9M+SBIPk9KimOkGU8d2LT9OIV3UgaNUR+52vGFOis0CQQCgKe7VBVH+sS7PVlGqkuaHrJEjxEqg2vN8tZIcTpzgwPN4IBG/XWkh+NfFOMujfUM4fzcjt3iBcqsrV/NQ6bXvAkBKzb3p/D6HSNMgRjH1nFLjOLul7jw1GzS3GMLKeFD6MMn6ZYS7Grc26ffjGmbB533+Xxe9+gG2vNPJNLlFmT/hAkBr3hH18dFZQSePiEkUIj+UXIqblhXU4+FcukSfP+q0C/9thduuEFFACgH319p+T1y4biVKrsRAGmRhmqhKdDyNAkB2OUycoj8NAHVjkADcodV8piiShlxuLUUBO0H7Pxal6UdkfXGboUUIE1gErykrEW35rPIaxFQcP7kwG/Frsahh";

    // 加密算法RSA
    public static final String KEY_ALGORITHM = "RSA";

    // 签名算法
    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";

    static class Body {

        private String playId;

        private int score;

        public Body() {
        }

        public String getPlayId() {
            return playId;
        }

        public void setPlayId(String playId) {
            this.playId = playId;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

    // 生成公私钥
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put("PUBLIC_KEY", publicKey);
        keyMap.put("PRIVATE_KEY", privateKey);
        //System.out.println("public-key:" + JsonUtil.fromObject(publicKey.getEncoded()));
        //System.out.println("private-key:" + JsonUtil.fromObject(privateKey.getEncoded()));
        return keyMap;
    }

    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey.getBytes());
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        return privateK;
    }

    public static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey pubkey = keyFactory.generatePublic(keySpec);
        return pubkey;
    }

    public static String sign(String encryData, String privateKey) throws Exception {
        byte[] data = encryData.getBytes();
        PrivateKey privateK = getPrivateKey(privateKey);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return new String(Base64.getEncoder().encode(signature.sign()));
    }

    public static boolean verify(String encryData, String publicKey, String sign) throws Exception {
        byte[] data = encryData.getBytes();
        PublicKey publicK = getPublicKey(publicKey);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        byte[] bsign = Base64.getDecoder().decode(sign.getBytes());
        return signature.verify(bsign);
    }

    public static void main(String[] args) throws Exception {
        String gameId = "1001";
        String playerId = "888888888";
        Body reqBody = new Body();
        reqBody.setPlayId("1234567");
        reqBody.setScore(-1000);
        //String bodyJson = JsonUtil.fromObject(reqBody);
        String bodyJson = null;
        System.out.println(bodyJson);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String beforeSign = gameId + playerId + timestamp + bodyJson;
        //签名
        String signStr = sign(beforeSign, TEST_PRIVATE_KEY);
        System.out.println(signStr);

        //验签
        boolean flag = verify(beforeSign, TEST_PUBLIC_KEY, signStr);
        System.out.println(flag);
    }
}
