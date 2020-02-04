package com.gj.gmall.utils;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

/**
 * @Title: MD5Util.java
 * @Description: MD5加密 工具类
 * Company:FURUIBOKE.SCIENCE.AND.TECHNOLOGY
 */
public class MD5Util {

	// 返回加密的结果，可能为空
	public static String MD5EncodeUtf8(String origin) {
		// 定义私密的key字符串
		String Key = "19950704,gongjin666";
		origin = origin + Key;

		return MD5Encode(origin, "utf-8");
	}

	/**
	 * 返回大写MD5
	 * @param origin
	 * @param charsetname
	 */
	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname)) {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			}
			else {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
			}
		} catch (Exception exception) {
			System.out.println("***** exception" + exception.getMessage());
		}

		if (StringUtils.isBlank(resultString)) return null;
		else return resultString.toUpperCase();
	}

	//这里主要是遍历8个byte，转化为16位进制的字符，即0-F
	private static String byteArrayToHexString(byte b[]) {
		StringBuilder resultSb = new StringBuilder();
		for (byte value : b) {
			resultSb.append(byteToHexString(value));
		}

		return resultSb.toString();
	}

	//这里是针对单个byte，256的byte通过16拆分为d1和d2
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) n += 256;
		int d1 = n / 16;
		int d2 = n % 16;

		return hexDigits[d1] + hexDigits[d2];
	}

	private static final String hexDigits[] = {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"
	};

}