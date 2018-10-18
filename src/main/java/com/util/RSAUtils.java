package com.util;

import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import org.springframework.security.crypto.codec.Base64;

/**
 * @Description:
 * @author: yingxu.pi@transsnet.com
 * @date: 2018年7月23日 下午2:25:59
 */
public class RSAUtils {

	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * 获取公钥的key
	 */
	public static final String PUBLIC_KEY = "RSAPublicKey";

	/**
	 * 获取私钥的key
	 */
	public static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;
	
	/**
	 *  测试公钥 
	 */
	public static final String TEST_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFYyhak5slMxWMrlBO/Eau5P7EvxdBwXrQPdVBKO2w7MiZ3b98iRI3oy2ELqorc0UFIoUyH3tirzcc0191m6Zt61/OLcUWUnzxOGfaNtG9MmPOf15EU7K3fMD4LTKrlWaQzns7QZmdFz/jkRGBvZ3HNoPtuPUT7sb1gCjUJaeefwIDAQAB";
	/**
	 * 	测试私钥
	 */
	public static final String TEST_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMVjKFqTmyUzFYyuUE78Rq7k/sS/F0HBetA91UEo7bDsyJndv3yJEjejLYQuqitzRQUihTIfe2KvNxzTX3Wbpm3rX84txRZSfPE4Z9o20b0yY85/XkRTsrd8wPgtMquVZpDOeztBmZ0XP+OREYG9ncc2g+249RPuxvWAKNQlp55/AgMBAAECgYEAkWr93c0E7aD27U+2hppBELRQJW6Kmb0K18PWCk0237NyDjlZy0vIigjDjbA7Wgtv+9p0unqLEib3uVrX5vMm5mSXk2cWX/474I8UkSvKSje9uCtX57nDJ1WMAmTeeD/A5bTeznvNuAodsm1SxSl+6g+IPDlvSG2Qw3rkISg4L6ECQQDp+XQXTSDUz35lfyunEkqML3BqvlU13WLoOIqxQqx25O8I49su7mHO5fYAd7zQvBU5EAoOMguplBX9vOuBF6iZAkEA1/f+Bxqigi9hLwie8zLNVm2hrYHrA7BblgKqHYBDVft6nHRJ8vCFdgaTJYhaszGG/KqZOeQ+89in/KDYcrb21wJBAN+IJ1UrprYqFkO5n2bansYXfHs+pAH2JExf2IFJhaOBTK1do0XPETqtkL0ZqBZz2oLNxA2T2niEtg3Ys9Z9V+ECQErsbeRpCRfA+CYpB3u3lCT3w6898xpEhIF2Sy4Q4UtjAxZkAYOWjbZ0cXgD5fNkqz/cr2u2E2DlOOIbqvuhHeECQH6CBr20hqyvF4CxbrHS48kXakpIHatGzkVCKeFolwwTGvMF72aPHPvDtOpOXNuQu+49VepY7ZV7V+j73TyaRdE=";

	
	
	/**
	 *  测试公钥 
	 */
	public static final String SEL_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3A8TbEPntSUALw6JcKQmpL+lzuQTrZpqX5W4UytPQCs+q2QIEpnwWV0MFOKoOqM/JOaT/CsjvA/lpBb7Y/TA7phn84XH8Rho2vBjShb647wEeKKomWYw9pfqYH048qaOJHGi1R2ggFZF3xj9dYieVBMY4V4cZOY/jrQrpiHfhU2lKYxzYYje1d2T9/HIICMxNtYW35oioPhLGWRvF+n/fMqBrhT8C3MvR++jD6wPSCga+ePpfTyZhb9d4gQ/NrIm5wtBYtVWNJ82YKgW9k+o0fFCWz8dEpWWXh/JnG13KtVpWRLI7fD4487AlfN0gp9QpX3YR+yUz8T/ai5V+NWd3QIDAQAB";
	/**
	 * 	测试私钥
	 */
	public static final String SEL_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDcDxNsQ+e1JQAvDolwpCakv6XO5BOtmmpflbhTK09AKz6rZAgSmfBZXQwU4qg6oz8k5pP8KyO8D+WkFvtj9MDumGfzhcfxGGja8GNKFvrjvAR4oqiZZjD2l+pgfTjypo4kcaLVHaCAVkXfGP11iJ5UExjhXhxk5j+OtCumId+FTaUpjHNhiN7V3ZP38cggIzE21hbfmiKg+EsZZG8X6f98yoGuFPwLcy9H76MPrA9IKBr54+l9PJmFv13iBD82sibnC0Fi1VY0nzZgqBb2T6jR8UJbPx0SlZZeH8mcbXcq1WlZEsjt8PjjzsCV83SCn1ClfdhH7JTPxP9qLlX41Z3dAgMBAAECggEBAKWWxpPKSToa3JeMRcm4C8ge2kLjhxc0QeUpQWl8BoePwvmvLQ/qPFzSUnwIznRJUQOQajzvrVUfVTrzfDmL+/3OOzKZMYnvRz+wcdZXknT5jxfDtkCwEBoO2HPA5rBYuk8cH15ki9jmkm89W8QsiI9VS3ySl6UpSRw14T6C8LJaBFIdDIXtO/daslQGvwm0W+laWnHPMbr1kw6Pm5yeiuqBvZzvUD+MVwzEvYZDW3Y7Z+cbmhA4K1mBxisQqPk5aIHWpRwRYBO51MOW9wI4g9rSY1DAmFNBqPWrv+Okom0EL/mbUJSj8zghtKu2jknLOQhqwAb/cQTL9DYJUzlC7vUCgYEA+U8FaFwmm6DVvlP+6M4AUkXQMYq+7H/ntOpRNMLYQbXAZOzIhESMQ22umPYmdE6Ost3FAWGMQTH+xNLvM4/zVMDLBUv9Je8hGP5P3FCuPVWgTrtDTl4pyzWP/OQoFI0kEIPLCBMIqT1SVHtZFfboctIA5nQ/2qBkhpYdj6nouY8CgYEA4fcU+a15N9yLZ7L8rD+PcfHww4qorvjvlNlccUdzwy5i1UZqOPBu4PdZ8zpEoSuN1xU5bAlCWPOnWZfX1jrrL4KplA82BcAfdmuHdcCJ4LRaY242LmC63ROTVk2ckddmNKLzbYcKzTkat1DcAXzqv1iC+B6rGjWJ8Zw00LKoA9MCgYBY+U+nwANzVUITC+0UbriEpC+6Dpc21ir+UQWsO8FsKg5AtpSVR9V7FwDRzD9LEOIyiZ0Och7ZAZu36MO/didUXv3VpRnyWNZKEjC0IwMcFpwAnSpXJJnr0PDTQyXet8RgNHjYJ9rWc3EXf3H6ucSZfcMee+xx/P4DXj2skOnilwKBgCobFHmB7biPDBsCqzqdjWk6IBcP22bfVCV0a1lgax3PovMC8AA0LTUWYliEXw15RDDz74NGJmrU3DZBqSJuUCzGODsyqmpf5nz2hINYoViRwtYMT2RM+gUABNBsHeS/MnhUdO/P3h9nqKbIFnbghA2rvJvnexKjL1UqOl05LT2pAoGBAJy7OqFonELLtinE4s6ouVlJjjlt3TKByZSVzJwsS00q09QBQ8NLtDQHr/rOotaEXUxpt+/6fwwD7dzLvEqo9VPf7jGYzaQGOkP6shCBWrAr+jbsd0XhST9F1ikWtpNcWlhs+JviKRS71Mhn1lKb4xErBOfI2KYjfLFCTVgD5dAV";

	
	/**
	 * 生成密钥对(公钥和私钥)
	 * 
	 * @throws Exception
	 */
	public static Map<String, Object> genKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	public static PublicKey getPublicKeyByStr (String publicKeyStr) {
		PublicKey publicK = null;
		try {
			byte[] keyBytes = Base64.decode(publicKeyStr.getBytes());
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			publicK = keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return publicK;
	}
	
	public static PrivateKey getPriKeyByStr (String priKeyStr) {
		PrivateKey privateK = null;
		try {
			byte[] keyBytes = Base64.decode(priKeyStr.getBytes());
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return privateK;
	}
	
	
	
	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @throws Exception
	 */
	public static String sign(byte[] data) throws Exception {
		byte[] keyBytes = Base64.decode(TEST_PRIVATE_KEY.getBytes());
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateK);
		signature.update(data);
		return new String(Base64.encode(signature.sign()));
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名
	 * @return
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String reqSign) throws Exception {
		String sign = URLDecoder.decode(reqSign, "utf-8");
		byte[] keyBytes = Base64.decode(TEST_PUBLIC_KEY.getBytes());
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(Base64.decode(sign.getBytes()));
	}

	/**
	 * 私钥解密
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
		byte[] keyBytes = Base64.decode(privateKey.getBytes());
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 公钥解密
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
		byte[] keyBytes = Base64.decode(publicKey.getBytes());
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 *            源数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		byte[] keyBytes = Base64.decode(publicKey.getBytes());
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * <p>
	 * 私钥加密
	 * </p>
	 * 
	 * @param data
	 *            源数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64.decode(privateKey.getBytes());
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * 获取私钥
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return new String(Base64.encode(key.getEncoded()));
	}

	/**
	 * 获取公钥
	 * 
	 * @param keyMap
	 *            密钥对
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return new String(Base64.encode(key.getEncoded()));
	}
	
	/**
	 * 获取TEST_PUBLIC_KEY
	 */
	public static String getTestPublicKey() throws Exception {
		return TEST_PUBLIC_KEY;
	}
	
	/**
	 * 获取TEST_PRIVATE_KEY
	 */
	public static String getTestPrivateKey() throws Exception {
		return TEST_PRIVATE_KEY;
	}
	
}