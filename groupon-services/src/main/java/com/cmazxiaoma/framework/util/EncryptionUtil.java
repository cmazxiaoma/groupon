package com.cmazxiaoma.framework.util;

import org.springframework.util.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 加密工具类
 */
@SuppressWarnings("restriction")
public final class EncryptionUtil {

	public static final String AES_ENCRYPT_KEY = "AESEncryptKey";

	/**
	 * MD5加密
	 * @param source	要加密的字符串
	 * @return 加密后的结果
	 */
	public static String MD5(String source) {
		// 32位加密md
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(source.getBytes(Charset.forName("UTF-8"))); // 更新
		byte[] bt = md.digest(); // 摘要

		// 保留结果的字符串
		StringBuilder sb = new StringBuilder();
		int p = 0;
		for (int i = 0; i < bt.length; i++) {
			p = bt[i];
			if (p < 0)
				p += 256; // 负值时的处理
			if (p < 16)
				sb.append("0");
			sb.append(Integer.toHexString(p));// 转换成16进制
		}
		return sb.toString();
	}

	/**
	 * AES加密
	 * @param source	需要加密的内容
	 * @return	加密后的字符串
	 */
	public static String encryptAES(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建密钥
			kgen.init(128, new SecureRandom(AES_ENCRYPT_KEY.getBytes()));
			byte[] enCodeFormat = kgen.generateKey().getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");

			Cipher cipher = Cipher.getInstance("AES");// 创建密码器

			cipher.init(Cipher.ENCRYPT_MODE, key);// 加密
			byte[] result = cipher.doFinal(source.getBytes("utf-8"));
			return parseByteArray2HexStr(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * AES解密
	 * @param encryptSource	密文
	 * @return	加密后的字符串
	 */
	public static String decryptAES(String encryptSource) {
		if (StringUtils.isEmpty(encryptSource)) {
			return null;
		}
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建密钥
			kgen.init(128, new SecureRandom(AES_ENCRYPT_KEY.getBytes()));
			byte[] enCodeFormat = kgen.generateKey().getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");

			// 解密
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(parseHexStr2ByteArray(encryptSource));
			return new String(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将二进制字节数组转换成十六进制字符串
	 * @param byteArray	 二进制字节数组
	 * @return	十六进制字符串
	 */
	public static String parseByteArray2HexStr(byte[] byteArray) {
		if (byteArray == null || byteArray.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			String hex = Integer.toHexString(byteArray[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	

	/**
	 * 将十六进制字符串转换为二进制字节数组
	 * @param hexStr	十六进制字符串
	 * @return	二进制字节数组
	 */
	public static byte[] parseHexStr2ByteArray(String hexStr) {
		if (StringUtils.isEmpty(hexStr) || hexStr.length() < 1) {
			return null;
		}
		
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	
	/**
	 * SHA1加密
	 * @param source	要加密的字符串
	 * @return 加密后的结果
	 */
	public static String encryptSHA1(String source) {
		return encrypt(source,"SHA-1");
	}
	
	private static String encrypt(String strSrc, String encName) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = parseByteArray2HexStr(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");
			return null;
		}
		return strDes;
	}
	
	/**
	 * BASE64加密
	 * @param source	要加密的字符串
	 * @return 加密后的结果
	 */
	public static String encryptBASE64(String source) {
		return new String(Base64.getDecoder().decode(source.getBytes()));
	}
	
	/**
	 * BASE64解密
	 * @param source	要加密的字符串
	 * @return 加密后的结果
	 */
	public static String decryptBASE64(String source) {
		return new String(Base64.getDecoder().decode(source.getBytes()));
	}

}